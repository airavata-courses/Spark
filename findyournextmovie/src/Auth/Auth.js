import React, { Component } from 'react';
import history from '../history';
import auth0 from 'auth0-js';
import { AUTH_CONFIG } from './auth0-variables';
import axios from 'axios';
import Alert from "react-s-alert";

export default class Auth {
  accessToken;
  idToken;
  expiresAt;
  userEmail;

  auth0 = new auth0.WebAuth({
    domain: AUTH_CONFIG.domain,
    clientID: AUTH_CONFIG.clientId,
    redirectUri: AUTH_CONFIG.callbackUrl,
    responseType: 'token id_token',
    scope: 'openid email'
  });

  constructor() {
    // super(props);
    console.log('in constructor');
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
    this.handleAuthentication = this.handleAuthentication.bind(this);
    this.isAuthenticated = this.isAuthenticated.bind(this);
    this.getAccessToken = this.getAccessToken.bind(this);
    this.getIdToken = this.getIdToken.bind(this);
    this.renewSession = this.renewSession.bind(this);
    this.getEmailId = this.getEmailId.bind(this);
  }

  login() {
    this.auth0.authorize();
  }

  handleAuthentication() {
    console.log('in handleAuthentication');
    this.auth0.parseHash((err, authResult) => {
      if (authResult && authResult.accessToken && authResult.idToken) {
        console.log('in if');
        this.setSession(authResult);
        console.log('session is set');

        let obj = { email: this.userEmail, password: ''};
        let loginRequest = Object.assign({}, obj);
        // console.log('loginRequest :: '+ JSON.stringify(loginRequest));

        axios.post('http://149.165.169.128:30003/api/login',  loginRequest )
            .then(res => {
              //localStorage.setItem('ACCESS_TOKEN', res.data.user_id);
              //localStorage.setItem('isAuthenticated', true);
              Alert.success("Login successful auth.js");
              this.props.history.push("/moviehome");
            }).catch(error => {
              // localStorage.setItem('isAuthenticated', false);
              Alert.error("Sorry! Some error occurred. auth.js");
            });
      } else if (err) {
        // history.replace('/login');
        console.log('error found :: ' + JSON.stringify(err));
        alert(`Error: ${err.error}. Check the console for further details.`);
      }
    });
  }

  getAccessToken() {
    return this.accessToken;
  }

  getIdToken() {
    return this.idToken;
  }

  getEmailId() {
    return this.email;
  }

  setSession(authResult) {
    // Set isLoggedIn flag in localStorage
    localStorage.setItem('isLoggedIn', 'true');
    // Set the time that the access token will expire at
    let expiresAt = (authResult.expiresIn * 1000) + new Date().getTime();
    this.accessToken = authResult.accessToken;
    this.idToken = authResult.idToken;
    this.expiresAt = expiresAt;
    this.userEmail = authResult.idTokenPayload.email;
    // navigate to the moviehome route
    history.replace('/moviehome');
  }

  renewSession() {
    this.auth0.checkSession({}, (err, authResult) => {
       if (authResult && authResult.accessToken && authResult.idToken) {
         this.setSession(authResult);
       } else if (err) {
         this.logout();
         console.log(err);
         alert(`Could not get a new token (${err.error}: ${err.error_description}).`);
       }
    });
  }

  logout() {
    // Remove tokens and expiry time
    this.accessToken = null;
    this.idToken = null;
    this.expiresAt = 0;

    // Remove isLoggedIn flag from localStorage
    localStorage.removeItem('isLoggedIn');

    this.auth0.logout({
      return_to: window.location.origin
    });

    // navigate to the moviehome route
    history.replace('/moviehome');
  }

  isAuthenticated() {
    // Check whether the current time is past the
    // access token's expiry time
    let expiresAt = this.expiresAt;
    return new Date().getTime() < expiresAt;
  }
}
