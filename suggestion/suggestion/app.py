import json
import requests
from flask import Flask, jsonify, request
from flask import Flask, current_app
# from flask_restful import reqparse, Api, Resource, abort
from flask_cors import CORS, cross_origin

from movie import Movie

app = Flask(__name__)

# URL for the database microservices
Db_URL = "http://localhost:8080/usermovierating"

# TMDb url
TMDb_URL = "https://api.themoviedb.org/3/"
API_KEY = "066f82f3715ba0beb02e8a92d3f1f31f"
NUMBER_OF_TOP_RATED_MOVIES = 3
NUMBER_OF_TOP_LIKED_GENRE = 3
NUMBER_OF_RECOMMENDATIONS = 10

@app.route('/test', methods=['GET'])
@cross_origin(origin='localhost',headers=['Content- Type','Authorization'])
def my_test():
    response = requests.get('http://test.com')
    print(response.json())
    return jsonify(response.json())

@app.route('/suggestion', methods=['GET'])
@cross_origin(origin='localhost',headers=['Content- Type','Authorization'])
def suggestion():

        user_id = request.args.get('userId')
        if user_id is None:
            return ("User Id cannot be blank"), 400
        try:
            db_url = Db_URL + "/getbyuserid"
            params = {"user_id" : user_id}
            movie_ratings = json.loads(requests.get(url=db_url, params=params).text)

            # Creation of a datastructure which stores the movie id and its corresponding rating
            seen_movies = dict()
            for x in movie_ratings:
                seen_movies[x['movieId']] = x['rating']

            # If no movie has been rated by the user, show the popular movies
            if len(seen_movies.keys())==0:
                recommendation_url = TMDb_URL + "movie/popular"
                params = {'api_key':API_KEY, 'language' : 'en-US', 'page' : 1}
                response = requests.get(url = recommendation_url, params = params)
                if response.status_code == requests.codes.ok:
                    suggestions = list(json.loads(response.text)['results'])
                recommended_movies = sorted(suggestions, key=lambda k :k['popularity'], reverse=True)[:NUMBER_OF_RECOMMENDATIONS]

            else:
                if len(seen_movies.keys())<=3:
                    top_rated_movies = seen_movies.keys()
                else:
                    top_rated_movies = sorted(seen_movies, key=seen_movies.get, reverse=True)[:NUMBER_OF_TOP_RATED_MOVIES]

                # To find recommendation, find the top 3 rated movie by the user, find movies similar to that using the api
                suggestions = list()
                for movie in top_rated_movies:
                    recommendation_url = TMDb_URL + "movie/" + str(movie) + "/similar"
                    params = {'api_key':API_KEY, 'language' : 'en-US', 'page' : 1}
                    response = requests.get(url = recommendation_url, params = params)
                    if response.status_code == requests.codes.ok:
                        suggestions = suggestions + json.loads(response.text)['results']

                # Filter out the already seen movies from the suggestions list
                suggestions = list(filter(lambda x:x['id'] not in seen_movies.keys(), suggestions))
                # Find the top 10 recommended movies by sorting on popularity index in the details of movie returned by TMDb
                recommended_movies = sorted(suggestions, key=lambda k :k['popularity'], reverse=True)[:NUMBER_OF_RECOMMENDATIONS]

            result = list()
            for movie in recommended_movies:
                result.append(Movie(movie['id'],
                            movie['poster_path'],
                            movie['original_language'],
                            movie['release_date'],
                            movie['title'],
                            movie['vote_count'],
                            movie['vote_average']).returndict())
            return jsonify(dict({"movies":result})), 200

        except Exception as e:
            return jsonify(str(e)), 400

# api.add_resource(Suggestion, "/suggestion")

if __name__ == "__main__":
    app.run(debug = True, host='0.0.0.0')
