import React, { Component } from 'react';
import './App.css';
import { Route, Switch } from "react-router-dom";
import LogIn from "./Login";
import SignIn from "./Signin";

class App extends Component {
  render() {
    return (
      <div className="App">
        <LogIn></LogIn>
        <SignIn></SignIn>
      </div>
    );
  }
}

export default App;
