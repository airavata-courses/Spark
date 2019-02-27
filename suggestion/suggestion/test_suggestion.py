import app
import json
import requests, requests_mock
from configparser import ConfigParser

parser = ConfigParser()
parser.read('config.ini')
api_key = parser.get('tmdb', 'api_key')
db_url = parser.get('rating','db_url') + '/getbyuserid'
tmdb_url = parser.get('tmdb', 'tmdb_url')

tmdb_url1 = tmdb_url + 'movie/popular'
# https://api.themoviedb.org/3/movie/324857/similar?api_key=066f82f3715ba0beb02e8a92d3f1f31f&language=en-US&page=1
tmdb_url2 = tmdb_url + "movie/324857/similar?api_key=" + api_key + "&language=en-US&page=1"
tmdb_url3 = tmdb_url + "movie/480530/similar?api_key=" + api_key + "&language=en-US&page=1"
tmdb_url4 = tmdb_url + "movie/99861/similar?api_key=" + api_key + "&language=en-US&page=1"

test_app = app.app
test_client = test_app.test_client()
test_app.testing = True

def test_suggestion_when_user_has_no_rated_movies():
    with requests_mock.Mocker() as m:
        m.register_uri('GET', url = db_url, json=[])
        m.register_uri('GET', url = tmdb_url1, real_http=True)
        resp = test_client.get('/suggestion', query_string={'userId': 1})
    # print(resp.data)
    assert requests.codes.ok == resp.status_code
    assert len(json.loads(resp.data)['movies']) != 0

def test_suggestion_when_user_has_rated_two_movies():
    with requests_mock.Mocker() as m:
        m.register_uri('GET', url = db_url, json=[{"userId":1, "movieId":324857, "rating":4},{"userId":1, "movieId":480530, "rating":3.5}])
        m.register_uri('GET', url = tmdb_url2, real_http=True)
        m.register_uri('GET', url = tmdb_url3, real_http=True)
        resp = test_client.get('/suggestion', query_string={'userId': 1})
    # print(resp.data)
    assert requests.codes.ok == resp.status_code
    assert len(json.loads(resp.data)['movies']) != 0

def test_suggestion_when_user_has_rated_more_than_three_movies():
    with requests_mock.Mocker() as m:
        m.register_uri('GET', url = db_url, json=[{"userId":1, "movieId":324857, "rating":4},
                                                  {"userId":1, "movieId":480530, "rating":3.5},
                                                  {"userId": 1, "movieId": 99861, "rating": 3.5},
                                                  {"userId": 1, "movieId": 312221, "rating": 3},
                                                  ])
        m.register_uri('GET', url = tmdb_url2, real_http=True)
        m.register_uri('GET', url = tmdb_url3, real_http=True)
        m.register_uri('GET', url = tmdb_url4, real_http=True)
        resp = test_client.get('/suggestion', query_string={'userId': 1})
    # print(resp.data)
    assert requests.codes.ok == resp.status_code
    assert len(json.loads(resp.data)['movies']) != 0

