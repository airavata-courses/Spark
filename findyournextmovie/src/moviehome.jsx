import React, {Component} from 'react';
import classNames from 'classnames';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { withStyles } from '@material-ui/core/styles';
import SearchBar from 'material-ui-search-bar'
import axios from 'axios';
import { Link } from "react-router-dom";
import NonEditableStarRating from './NonEditableStarRating';
import Alert from "react-s-alert";

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
});

class MovieHome extends Component{
  constructor(props) {
      super(props);
      this.state = {
        movieDetails: [],
        movieName: "",
        imgPath: "",
        searchText: "",
        isAuthenticated: "",
        userId: "",
      };
      this.requestSearch = this.requestSearch.bind(this);
      this.onClick = this.requestSearch.bind(this);
  }

  componentWillMount() {
    this.setState({
      isAuthenticated: localStorage.getItem("isAuthenticated"),
      userId: localStorage.getItem("ACCESS_TOKEN"),
    });

    axios.get('http://149.165.169.90:80/search/toprated')
        .then(res => {
          this.setState({
            movieDetails: res.data.movies,
            imgPath: "https://image.tmdb.org/t/p/w500/" + res.data.movies.poster_path,
          });
        }).catch(error => {
          Alert.error("Sorry! Some error occurred.");
        });
    }

  requestSearch(){

    if(this.state.searchText == '' || this.state.searchText == ' ' || this.state.searchText == null)
      Alert.error("Please enter a valid movie name.");
    else{
        axios.get('http://149.165.169.90:80/search/keyword?keyword=' +  this.state.searchText)
            .then(res => {
              this.setState({
                movieDetails: res.data.movies,
              });
            }).catch(error => {
              Alert.error("Sorry! Some error occurred.");
            });
        }
    }

  handleChange(event){
    this.setState({
      searchText: event,
    });
  }

  login() {
    this.props.auth.login();
  }

  getSuggestions(){
      axios.get('http://149.165.169.90:80/suggestion?userId=' + localStorage.getItem('ACCESS_TOKEN') )
          .then(res => {
            this.setState({
              movieDetails: res.data.movies,
            });        }).catch(error => {
              Alert.error("Sorry! Some error occurred.");
          });
      }

  render(){

    const { isAuthenticated } = this.props.auth;

    const { classes } = this.props;
    return(

      <React.Fragment>
        <CssBaseline />

        <main>
        <SearchBar id = "searchText"
           onRequestSearch={this.requestSearch}
           onChange={this.handleChange.bind(this)}
           style={{
             margin: '0 auto',
             maxWidth: 800, marginTop: '3%'
           }}
         />
         <Button
          style = {{marginTop: '2%', backgroundColor: '#DCDCDC', width: '15%'}}
           onClick={this.requestSearch}> Search
         </Button>
         {
              isAuthenticated() && (
                <Button
                  style = {{marginTop: '2%', backgroundColor: '#DCDCDC', marginLeft: '3%', width: '15%'}}
                  onClick = {this.getSuggestions.bind(this)}
                  >
                   Get Suggestions
                   </Button>
                )
          }

          {
              !isAuthenticated() && (
                <Button
                  style = {{marginTop: '2%', backgroundColor: '#DCDCDC', marginLeft: '3%', width: '15%'}}
                  onClick = {this.login.bind(this)}
                  >
                  Get Suggestions
                  </Button>
                )
          }

          <div className={classNames(classes.layout, classes.cardGrid)}>
            {/* End hero unit */}
            <Grid container spacing={40}>
              {this.state.movieDetails.map(card => (
                <Grid item key={card} sm={6} md={4} lg={3}>
                <Link to= {"/movieDetails/" + card["movie_id"]} style={{textDecoration: 'none'}}>
                  <Card className={classes.card} >
                  <CardMedia
                  component="img"
                    className={classes.media}
                    image= {"https://image.tmdb.org/t/p/w500/" + card['poster_path']} // eslint-disable-line max-len
                    title={card["title"]}
                  />
                  <CardContent className={classes.cardContent} style={{marginTop:'5px'}}>
                      <Typography gutterBottom variant="h5" component="h2">
                        {card["title"]}
                      </Typography>

                    </CardContent>
                    <CardActions>
                      <Typography gutterBottom variant="h10" component="h5">
                      <NonEditableStarRating rating = {card["vote_average"]}/>
                      </Typography>

                    </CardActions>
                  </Card>
                  </Link>

                  </Grid>
              ))}

            </Grid>
          </div>
        </main>
      </React.Fragment>
    );
  }
}

export default withStyles(styles)(MovieHome);
