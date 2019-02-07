import React, { Component } from 'react';
import './App.css';
import { Switch } from "react-router-dom";
import MenuAppBar from './MenuAppBar';
import MovieDetails from './MovieDetails';
import Jumbotron from "./home";
import { BrowserRouter } from 'react-router-dom';
import Route from 'react-router-dom/Route';
import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import LogIn from "./Login";
import SignIn from "./Signin";

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <MenuAppBar></MenuAppBar>
          <Switch>
            <Route exact path = "/" component={Jumbotron}></Route>
            <Route exact path = "/home" component={Jumbotron}></Route>
            <Route exact path = "/movieDetails/:movie_id" component={MovieDetails}></Route>
            <Route exact path = "/login" component={LogIn}></Route>
            <Route exact path = "/signin" component={SignIn}></Route>
          </Switch>
          <Alert
            stack={{ limit: 3 }}
            timeout={3000}
            position="bottom-right"
            effect="slide"
            offset={5}
          />
        </div>

      </BrowserRouter>

    );
  }
}

export default App;
