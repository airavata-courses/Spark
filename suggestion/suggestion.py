from database import Base, UserMovieRating, MovieGenre
from movie import Movie

import json
import requests
from flask import Flask, jsonify
from flask_restful import reqparse, Api, Resource, abort
from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine


app = Flask(__name__)
api = Api(app)

# To parse the arguments in the requests
parser = reqparse.RequestParser()

# Creation of sql engine and database session to talk to database (read, write and delete rows from the tables within movie schema)
engine = create_engine('mysql+pymysql://' + 'root' + ':' + 'aimhigher01' + '@' + 'localhost' +':' + '3306' + '/' + 'movie')
engine.connect()
Base.metadata.bind = engine
DBSession = sessionmaker(autoflush=True, bind=engine)
dbSession = DBSession()

# TMDb url
TMDb_URL = "https://api.themoviedb.org/3/"
API_KEY = "066f82f3715ba0beb02e8a92d3f1f31f"
NUMBER_OF_TOP_RATED_MOVIES = 3
NUMBER_OF_TOP_LIKED_GENRE = 3
NUMBER_OF_RECOMMENDATIONS = 10

class Suggestion(Resource):
    def get(self):
        parser.add_argument('user_id', required=True, help="User_id cannot be blank!")
        args = parser.parse_args()
        user_id = args['user_id']
        try:
            movie_ratings = dbSession.query(UserMovieRating).filter_by(user_id=user_id)

            # Creation of a datastructure which stores the movie id and its corresponding rating
            data = dict()
            for x in movie_ratings:
                data[x.movie_id] = x.rating

            if len(data.keys())<=3:
                top_rated_movies = data.get
            else:
                top_rated_movies = sorted(data, key=data.get, reverse=True)[:NUMBER_OF_TOP_RATED_MOVIES]

            # To find recommendation, find the top 3 rated movie by the user, find movies similar to that using the api
            suggestions = list()
            for movie in top_rated_movies:
                recommendation_url = TMDb_URL + "movie/" + str(movie) + "/similar"
                params = {'api_key':API_KEY, 'language' : 'en-US', 'page' : 1}
                response = requests.get(url = recommendation_url, params = params)
                if response.status_code == requests.codes.ok:
                    suggestions = suggestions + json.loads(response.text)['results']

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
            return jsonify(result)

        except Exception as e:
            abort(400, message=str(e))

api.add_resource(Suggestion, "/suggestion")

if __name__ == "__main__":
    app.run(debug = True)
