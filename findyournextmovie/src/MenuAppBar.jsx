import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Switch from '@material-ui/core/Switch';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import MenuItem from '@material-ui/core/MenuItem';
import Menu from '@material-ui/core/Menu';
import { Link } from "react-router-dom";
import Button from '@material-ui/core/Button';

const styles = {
  root: {
    flexGrow: 1,
  },
  grow: {
    flexGrow: 1,
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
  },
};

class MenuAppBar extends React.Component {
  state = {
    auth: true,
    anchorEl: null,
  };

  handleChange = event => {
    this.setState({ auth: event.target.checked });
  };

  handleMenu = event => {
    this.setState({ anchorEl: event.currentTarget });
  };

  handleClose = () => {
    this.setState({ anchorEl: null });
  };

  logout(){
    localStorage.setItem('isAuthenticated', false);
    localStorage.setItem('ACCESS_TOKEN', "");

  }

  render() {
    const { classes } = this.props;
    const { auth, anchorEl } = this.state;
    const open = Boolean(anchorEl);

    let logOption;
    if(localStorage.getItem('isAuthenticated') == true){
      logOption = <Link to= {"/home"} style={{textDecoration: 'none', width: '5%'}} onClick = {this.logout()}> Log Out </Link>
    }else{
      logOption = <Link to= {"/login"} style={{textDecoration: 'none', marginLeft: '2%', color: 'white', width: '5%'}}> Log In </Link>
    }
    return (
      <div className={classes.root}>

        <AppBar position="static" style={{backgroundColor: 'black'}}>
          <Toolbar>
            <Link to= {"/"} style = {{color: 'white', textAlign: 'center', textDecoration: 'none'}}>
              <Typography variant="h6" color="inherit" className={classes.grow}>
                Find Your Next Movie
              </Typography>
            </Link>
                <Typography variant="h10" color="white" style={{marginLeft: '2%', width: '5%'}}>

                {logOption}
                </Typography>
          </Toolbar>
        </AppBar>
      </div>
    );
  }
}

MenuAppBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(MenuAppBar);
