import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Button from '@material-ui/core/Button';
import CameraIcon from '@material-ui/icons/PhotoCamera';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import Footer from "./footer";
import SearchBar from 'material-ui-search-bar'
import axios from 'axios';
import AutoFitImage from 'react-image-autofit-frame';
import {Tabs, Tab} from '@material-ui/core/'

const styles = theme => ({
  icon: {
    marginRight: theme.spacing.unit * 2,
  },
  heroUnit: {
    backgroundColor: theme.palette.background.paper,
  },
  heroContent: {
    maxWidth: 600,
    margin: '0 auto',
    padding: `${theme.spacing.unit * 8}px 0 ${theme.spacing.unit * 6}px`,
  },
  heroButtons: {
    marginTop: theme.spacing.unit * 4,
  },
  layout: {
    width: 'auto',
    marginLeft: theme.spacing.unit * 3,
    marginRight: theme.spacing.unit * 3,
    [theme.breakpoints.up(1100 + theme.spacing.unit * 3 * 2)]: {
      width: 1100,
      marginLeft: 'auto',
      marginRight: 'auto',
    },
  },
  cardGrid: {
    padding: `${theme.spacing.unit * 8}px 0`,
  },
  card: {
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
  },
  media: {
     objectFit: 'contain',
     maxHeight: '65%',
   },
  cardContent: {
    flexGrow: 1,
  },
  footer: {
    backgroundColor: theme.palette.background.paper,
    padding: theme.spacing.unit * 6,
  },
});

const cards = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14];

class Jumbotron extends Component{
  constructor(props) {
      super(props);
      this.state = {
        movieDetails: [],
        movieName: "",
        imgPath: "",
      };
      this.requestSearch = this.requestSearch.bind(this);
    }
  componentWillMount() {
    axios.get('http://localhost:8080/search/toprated' )
        .then(res => {
          console.log('res :: ' + JSON.stringify(res.data.movies));
          console.log('path :: ' + res.data.movies.poster_path);

          this.setState({
            movieDetails: res.data.movies,
            imgPath: "https://image.tmdb.org/t/p/w500/" + res.data.movies.poster_path,
          });
        }).catch(error => {
          console.log('error!!');
        });
    }

    requestSearch(event){
      console.log("handleChange", event);
      axios.get('http://localhost:8080/search/keyword?keyword=' +  event)
          .then(res => {
            console.log(JSON.stringify(res));
            this.setState({
              movieDetails: res.data.movies,
            });
          }).catch(error => {
            console.log('error!!');
          });
    }

  render(){
    const { classes } = this.props;
    return(
      <React.Fragment>
        <CssBaseline />

        <main>
        <SearchBar id = "searchText"
           onRequestSearch={this.requestSearch}
           style={{
             margin: '0 auto',
             maxWidth: 800, marginTop: '3%'
           }}
         />
          <div className={classNames(classes.layout, classes.cardGrid)}>
            {/* End hero unit */}
            <Grid container spacing={40}>
              {this.state.movieDetails.map(card => (
                <Grid item key={card} sm={6} md={4} lg={3}>
                  <Card className={classes.card} >


                  <CardMedia
                  component="img"
                    className={classes.media}
                    image= {"https://image.tmdb.org/t/p/w500/" + card['poster_path']} // eslint-disable-line max-len
                    title="Image title"
                  />
                  <CardContent className={classes.cardContent} style={{marginTop:'5px'}}>
                      <Typography gutterBottom variant="h5" component="h2">
                        {card["title"]}
                      </Typography>

                    </CardContent>
                    <CardActions>
                      <Button size="small" color="primary">
                        View
                      </Button>
                      <Typography gutterBottom variant="h10" component="h5">
                        {card["vote_count"]}
                      </Typography>

                    </CardActions>
                  </Card>
                </Grid>
              ))}
            </Grid>
          </div>
        </main>
        {/* Footer */}
        <Footer/>
        {/* End footer */}
      </React.Fragment>
    );
  }
}

export default withStyles(styles)(Jumbotron);
