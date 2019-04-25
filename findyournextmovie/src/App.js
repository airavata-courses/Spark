import React, { Component } from 'react';
import './App.css';
import { Switch } from "react-router-dom";
import MenuAppBar from './MenuAppBar';
import MovieDetails from './MovieDetails';
import MovieHome from "./moviehome";
import { BrowserRouter } from 'react-router-dom';
import Route from 'react-router-dom/Route';
import Alert from "react-s-alert";
import "react-s-alert/dist/s-alert-default.css";
import "react-s-alert/dist/s-alert-css-effects/slide.css";
import LogIn from "./Login";
import SignIn from "./Signin";
import Callback from "./Callback/Callback";
import Auth from "./Auth/Auth";
import Home from './Home/Home';

console.log('in app js');

const auth = new Auth();
const handleAuthentication = ({location}) => {
  console.log('calling handleAuthentication check if condition ');
  if (/access_token|id_token|error/.test(location.hash)) {
    console.log('calling handleAuthentication');
    auth.handleAuthentication();
  }
}

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App">
          <MenuAppBar auth={auth} ></MenuAppBar>
          <Switch>
          <Route exact path = "/" component={MovieHome}></Route>

            <Route path="/home" render={(props) => <Home auth={auth} {...props} />} />
            <Route exact path = "/moviehome" component={MovieHome}></Route>
            <Route exact path = "/movieDetails/:movie_id" component={MovieDetails}></Route>
            <Route exact path = "/login" component={LogIn}></Route>
            <Route exact path = "/signin" component={SignIn}></Route>
            <Route exact path="/callback" render={(props) => {
              handleAuthentication(props);
              return <Callback {...props} />
            }}/>

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
