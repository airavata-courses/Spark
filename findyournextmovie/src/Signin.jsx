import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import FormControl from '@material-ui/core/FormControl';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Paper from '@material-ui/core/Paper';
import Typography from '@material-ui/core/Typography';
import withStyles from '@material-ui/core/styles/withStyles';
import muiTheme from './Theme';
import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import TextField from '@material-ui/core/TextField';
import axios from 'axios';
import Alert from "react-s-alert";
import { Link } from "react-router-dom";

const styles = theme => ({
  main: {
    width: 'auto',
    display: 'block', // Fix IE 11 issue.
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
      width: 400,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  paper: {
    marginTop: theme.spacing.unit * 8,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
  },
  avatar: {
    margin: theme.spacing.unit,
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing.unit,
  },
  submit: {
    marginTop: theme.spacing.unit * 3,
  },
  textField: {
    marginLeft: theme.spacing.unit,
    marginRight: theme.spacing.unit,
    width: 200,
  },
});


class SignIn extends Component{
  constructor(props) {
      super(props);
      this.state = {
        first_name: "",
        last_name: "",
        email: "",
        password: ""
      };
      this.handleInputChange = this.handleInputChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  handleInputChange(event) {
  const target = event.target;
  const inputName = target.name;
  const inputValue = target.value;

  this.setState({
    [inputName]: inputValue
  });
}

handleSubmit(event) {
  event.preventDefault();
  const signUpRequest = Object.assign({}, this.state);
  signUpRequest['dob'] = '01-01-1992';
  signUpRequest['gender'] = 'F';
  signUpRequest['country'] = 'India'

  axios.post('http://149.165.169.128:30003/api/register',  signUpRequest )
      .then(res => {
        Alert.success("Signed in successfully.");
        this.props.history.push("/login");
      }).catch(error => {
        Alert.error("Sorry! Some error occurred.");
      });
  }


  render(){
    const { classes } = this.props;
    return(
      <MuiThemeProvider theme={muiTheme}>
			<CssBaseline />
      <main className={classes.main}>
        <CssBaseline />
        <Paper className={classes.paper}>
          <Avatar className={classes.avatar}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Sign in
          </Typography>
          <form className={classes.form} onSubmit={this.handleSubmit}>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="text">First Name</InputLabel>
            <Input id="first_name" name="first_name" autoComplete="first name" onChange={this.handleInputChange} autoFocus />
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="text">Last Name</InputLabel>
            <Input id="last_name" name="last_name" autoComplete="last name" onChange={this.handleInputChange} autoFocus />
          </FormControl>
          <FormControl margin="normal" required fullWidth>
            <InputLabel htmlFor="email">Email</InputLabel>
            <Input id="email" name="email" autoComplete="email" onChange={this.handleInputChange} autoFocus />
          </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Password</InputLabel>
              <Input name="password" type="password" id="password" onChange={this.handleInputChange} autoComplete="current-password" />
            </FormControl>
            <FormControl margin="normal" required fullWidth>
              <InputLabel htmlFor="password">Confirm Password</InputLabel>
              <Input name="c_password" type="password" id="c_password" autoComplete="confirm-password" />
            </FormControl>
            <FormControl margin="normal" fullWidth>
              <Input name="dob" type="date" id="dob" autoComplete="dd-mm-yyyy" />
            </FormControl>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              color="primary"
              className={classes.submit}
            > Sign In
          </Button>
          <Typography gutterBottom variant="h10" component="h5" style={{marginTop:'2%'}}>
            Already have an account?
            <Link to= {"/login/"} style={{textDecoration: 'none'}}> Log In
            </Link>
            &nbsp;here
          </Typography>
          </form>
        </Paper>
      </main>
      </MuiThemeProvider>
    );
  }
}

SignIn.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SignIn);
