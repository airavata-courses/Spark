import React, { Component } from 'react';
import './App.css';
import MenuAppBar from './MenuAppBar';
import MovieDetails from './MovieDetails';
import MovieHome from "./moviehome";
import { Route, Router } from 'react-router-dom';
import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import LogIn from "./Login";
import SignIn from "./Signin";
import Callback from "./Callback/Callback";
import Auth from "./Auth/Auth";
import Home from './Home/Home';
import history from './history';

const auth = new Auth();
const handleAuthentication = ({location}) => {
  if (/access_token|id_token|error/.test(location.hash)) {
    console.log('calling handleAuthentication');
    auth.handleAuthentication();
  }
}

class App extends Component {
  render() {
    return (
      <Router history={history}>
        <div className="App">
          <MenuAppBar auth={auth} ></MenuAppBar>
          <Route exact path = "/" render={(props) => <MovieHome auth={auth} {...props} />} />
          <Route path="/moviehome" render={(props) => <MovieHome auth={auth} {...props} />} />
            <Route path = "/movieDetails/:movie_id" component={MovieDetails}></Route>
            <Route path = "/login" component={LogIn}></Route>
            <Route path = "/signin" component={SignIn}></Route>

            <Route path="/home" render={(props) => <Home auth={auth} {...props} />} />
            <Route exact path="/callback" render={(props) => {
                  console.log('route path handle auth called...');
                  handleAuthentication(props);
                  return <Callback {...props} />
              }}/>

          <Alert
            stack={{ limit: 3 }}
            timeout={3000}
            position="bottom-right"
            effect="slide"
            offset={5}
          />
        </div>

      </Router>

    );
  }
}

export default App;
