import json
import os
import requests
from flask import Flask, jsonify, request
from flask_cors import cross_origin
from configparser import ConfigParser
import socket

from movie import Movie

parser = ConfigParser()
if os.path.isfile('./config.ini'):
    parser.read('./config.ini')
else:
    print("error finding the config file")
    exit()

app = Flask(__name__)

# Service registry
SERVICE_REGISTRY_URL = parser.get('rating', 'zookeeper_url')

# TMDb url
TMDb_URL = parser.get('tmdb','tmdb_url')
API_KEY = parser.get('tmdb','api_key')
NUMBER_OF_TOP_RATED_MOVIES = int(parser.get('constants','number_of_top_rated_movies'))
NUMBER_OF_TOP_LIKED_GENRE = int(parser.get('constants','number_of_top_liked_genre'))
NUMBER_OF_RECOMMENDATIONS = int(parser.get('constants','number_of_recommendations'))

@app.route('/suggestion', methods=['GET'])
@cross_origin(origin='localhost',headers=['Content- Type','Authorization'])
def suggestion():

        user_id = request.args.get('userId')
        if user_id is None:
            return "User Id cannot be blank", 400
        try:
            db_url = discover_rating()
            if db_url is None:
                return "Unable to discover rating service in zookeeper", 400
            db_url = db_url + "/getbyuserid"
            params = {"user_id" : user_id}
            movie_ratings = json.loads(requests.get(url=db_url, params=params).text)

            # Creation of a data structure which stores the movie id and its corresponding rating
            seen_movies = dict()
            for x in movie_ratings:
                seen_movies[x['movieId']] = x['rating']

            # If no movie has been rated by the user, show the popular movies
            if len(seen_movies.keys()) == 0:
                recommendation_url = TMDb_URL + "movie/popular"
                params = {'api_key':API_KEY, 'language': 'en-US', 'page' : 1}
                response = requests.get(url = recommendation_url, params = params)
                if response.status_code == requests.codes.ok:
                    suggestions = list(json.loads(response.text)['results'])
                    recommended_movies = sorted(suggestions, key=lambda k:k['popularity'],
                                                reverse=True)[:NUMBER_OF_RECOMMENDATIONS]
                else:
                    return 'TMDb api not working', 400

            else:
                if len(seen_movies.keys()) <= 3:
                    top_rated_movies = seen_movies.keys()
                else:
                    top_rated_movies = sorted(seen_movies, key=seen_movies.get,
                                              reverse=True)[:NUMBER_OF_TOP_RATED_MOVIES]

                # To find recommendation, find the top 3 rated movie by the user, find movies similar to that using the
                # api
                suggestions = list()
                for movie in top_rated_movies:
                    recommendation_url = TMDb_URL + "movie/" + str(movie) + "/similar"
                    params = {'api_key': API_KEY, 'language': 'en-US', 'page': 1}
                    response = requests.get(url=recommendation_url, params=params)
                    if response.status_code == requests.codes.ok:
                        suggestions = suggestions + json.loads(response.text)['results']

                # Filter out the already seen movies from the suggestions list
                suggestions = list(filter(lambda x:x['id'] not in seen_movies.keys(), suggestions))
                # Find the top 10 recommended movies by sorting on popularity index in details of movie returned by TMDb
                recommended_movies = sorted(suggestions, key=lambda k :k['popularity'],
                                            reverse=True)[:NUMBER_OF_RECOMMENDATIONS]

            result = list()
            for movie in recommended_movies:
                result.append(Movie(movie['id'],
                                    movie['poster_path'],
                                    movie['original_language'],
                                    movie['release_date'],
                                    movie['title'],
                                    movie['vote_count'],
                                    movie['vote_average']).returndict())
            return jsonify(dict({"movies": result})), 200

        except Exception as e:
            return jsonify(str(e)), 400


# Below method registers the suggestion service on Zookeeper using the servicergistry service
def register_suggestion():
    try:
        hostname = socket.gethostname()
        uri = 'http://' + str(socket.gethostbyname(hostname)) + ":5000"
        service_registry_url = SERVICE_REGISTRY_URL + "/register"
        params = {"name": "suggestion", "uri": uri}
        requests.post(url=service_registry_url, params=params)
        return True
    except Exception as e:
        print(str(e))
        return False


# Below method gets the uri corresponding to rating uri by using the serviceregistry service
def discover_rating():
    try:
        zookeeper_url = SERVICE_REGISTRY_URL + "/discover"
        params = {"name": "rating"}
        uri = requests.get(url=zookeeper_url, params=params)
        print(uri.text)
        return uri
    except:
        return None


if __name__ == "__main__":
    if register_suggestion():
        print("Suggestion service registered")
    else:
        print("Suggestion service unable to register")

    app.run(debug=True, host='0.0.0.0', use_reloader=False)




