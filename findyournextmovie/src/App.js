import React, { Component } from 'react';
import './App.css';
import { Switch } from "react-router-dom";
import MenuAppBar from './MenuAppBar';
import MovieDetails from './MovieDetails';
import Jumbotron from "./home";
import { BrowserRouter } from 'react-router-dom';
import Route from 'react-router-dom/Route';
class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <MenuAppBar></MenuAppBar>
          <Switch>
            <Route exact path = "/" component={Jumbotron}></Route>
            <Route exact path = "/movieDetails/:movie_id" component={MovieDetails}></Route>
          </Switch>
        </div>
      </BrowserRouter>

    );
  }
}

export default App;
