import React, { Component } from 'react';
import { Route, Switch } from "react-router-dom";
import axios from 'axios';
import AutoFitImage from 'react-image-autofit-frame';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Button from '@material-ui/core/Button';
import CameraIcon from '@material-ui/icons/PhotoCamera';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Image from 'material-ui-image';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';
import IconButton from '@material-ui/core/IconButton';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import StarRating from './StarRating';
import Alert from "react-s-alert";
import NonEditableStarRating from './NonEditableStarRating';

const styles = theme => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing.unit * 2,
    textAlign: 'left',
    color: theme.palette.text.secondary,
  },
  image: {
        width: '100%',
        height: '100%',
        resizeMode: "contain",

    },
    root2: {
    display: 'flex',
    flexWrap: 'wrap',
    justifyContent: 'space-around',
    overflow: 'hidden',
    backgroundColor: theme.palette.background.paper,
  },
  gridList: {
    flexWrap: 'nowrap',
    // Promote the list into his own layer on Chrome. This cost memory but helps keeping high FPS.
    transform: 'translateZ(0)',
  },
  title: {
    color: theme.palette.primary,
  },
  titleBar: {
    background:
      'linear-gradient(to top, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.3) 70%, rgba(0,0,0,0) 100%)',
  },
});

class MovieDetails extends Component {
  constructor(props) {
      super(props);
      this.state = {
        movie_id: '',
        movieDetails: [],
        movieCast : [],
        movieGenre : [],
      };
    }

  componentWillMount() {
    axios.get(localStorage.getItem("search")+'/movie/details?movieId=' +  this.props.match.params.movie_id)
        .then(res => {
          this.setState({
            movieDetails: res.data,
            movieCast: res.data.cast,
            movieGenre: res.data.genres,
            movie_id: this.props.match.params.movie_id,
          });
        }).catch(error => {
          Alert.error("Sorry! Some error occurred.");
          //jenkins test build
        });
    }

  render(){
    const { classes } = this.props;
    const items = this.state.movieCast.map((item, key) =>
                    <GridListTile key={item.profile_path}>
                      <img src={"https://image.tmdb.org/t/p/w500/" + item.profile_path} alt = {item.character}/>
                      <GridListTileBar
                        title={item.name}
                        classes={{
                          root: classes.titleBar,
                          title: classes.title,
                        }}
                        actionIcon={
                          <IconButton>
                            <StarBorderIcon className={classes.title} />
                          </IconButton>
                        }
                      />
                    </GridListTile>

    );

    const genre = this.state.movieGenre.map((item, key) =>
      <span> {item.name} </span>
    );
    let star;
    if(localStorage.getItem('isAuthenticated') === null || localStorage.getItem('isAuthenticated') == "false"){
      star = <NonEditableStarRating rating = {0}/>
    }else{
      star = <StarRating movie_id = {this.props.match.params.movie_id}
      movie_name = {this.state.movieDetails['title']}/>
    }

    return(
<div className={classes.root} style={{marginTop: '2%'}}>
     <Grid container spacing={24}>
       <Grid item xs={10} style = {{marginLeft:'5%', maxWidth: '40%', height: '50%'}}>
            <Image
                   style={styles.image}
                   resizeMode={'contain'}   /* <= changed  */
                   src={'https://image.tmdb.org/t/p/w500/' + this.state.movieDetails['poster_path']} />
       </Grid>
       <Grid item xs={12} sm={6} style = {{marginLeft:'2%'}}>
         <Paper className={classes.paper}>
           <Typography gutterBottom variant="h10" component="h1">
             {this.state.movieDetails['title']}
           </Typography>

           <Typography gutterBottom variant="h10" component="h5">
           {star}
          Vote Average: {this.state.movieDetails['vote_average']}
           </Typography>

           <Typography gutterBottom variant="h10" component="h3">
             {this.state.movieDetails['tagline']}
           </Typography>

           <Typography gutterBottom variant="h7" component="h3">
             Overview:
           </Typography>
           <Typography gutterBottom variant="h7" component="h5">
            {this.state.movieDetails['overview']}
           </Typography>

           <Typography gutterBottom variant="h7" component="h3">
            Adult:
           </Typography>
           <Typography gutterBottom variant="h7" component="h5">
               {(() => {
                switch (this.state.movieDetails['adult']) {
                  case false:   return "No";
                  case true: return "Yes";
                  default: return "Nooooo";
                }
              })()}
          </Typography>

          <Typography gutterBottom variant="h7" component="h3">
           Release Date :
          </Typography>
          <Typography gutterBottom variant="h7" component="h5">
            {this.state.movieDetails['release_date']}
          </Typography>

          <Typography gutterBottom variant="h7" component="h3">
           Feature Length :
          </Typography>
          <Typography gutterBottom variant="h7" component="h5">
            {this.state.movieDetails['runtime']}
          </Typography>

          <Typography gutterBottom variant="h7" component="h3">
           Genre :
          </Typography>

          <Typography gutterBottom variant="h7" component="h5">
            {genre}
          </Typography>

         </Paper>
       </Grid>
       <Grid item xs={6} sm={11} style = {{marginLeft:'5%', textAlign: 'left', color: '#707070'}}>
          <Typography gutterBottom variant="h7" component="h3">
           Cast:
          </Typography>

            <div className={classes.root2}>
                <GridList className={classes.gridList} cols={5}>
                {items}
                </GridList>
              </div>
        </Grid>


     </Grid>
   </div>
    );
  }
}

MovieDetails.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default  withStyles(styles)(MovieDetails);
