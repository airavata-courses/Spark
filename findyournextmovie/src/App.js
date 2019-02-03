import React, { Component } from 'react';
import './App.css';
import { Route, Switch } from "react-router-dom";
import MenuAppBar from './MenuAppBar';
// import LogIn from "./Login";
// import SignIn from "./Signin";
import Jumbotron from "./home"
class App extends Component {
  render() {
    return (
      <div className="App">
        <MenuAppBar></MenuAppBar>
        <Jumbotron></Jumbotron>
      </div>
    );
  }
}

export default App;
