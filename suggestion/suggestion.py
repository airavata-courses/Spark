import requests
import json
from flask import Flask

app = Flask(__name__)

@app.route('/')
def index():
    url = "https://api.themoviedb.org/3/search/movie?api_key=066f82f3715ba0beb02e8a92d3f1f31f&language=en-US&query=Natalie"
    response =  requests.get(url = url)
    print("something")
    # For successful API call, response code will be 200 (OK)
    if(response.ok):
        # jData = json.loads(response.content)
        # print("something2")
        return(response.content)
    else:
        print("something3")
        return 'This is homepage'

if __name__ == "__main__":
    app.run(debug = True)
