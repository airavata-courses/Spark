import React from 'react';
import ReactDOM from 'react-dom';
import StarRatingComponent from 'react-star-rating-component';
import axios from 'axios';
import Alert from "react-s-alert";
import { createHashHistory } from 'history';
export const history = createHashHistory()

class StarRating extends React.Component {
  constructor() {
    super();

    this.state = {
      rating: 0,
      movieId: '',
    };
    this.onStarClick = this.onStarClick.bind(this);
  }

  componentWillMount() {
    this.setState({
      movieId: this.props.movie_id,
    });
    if(localStorage.getItem("isAuthenticated") == "true"){
        axios.get('http://149.165.169.90:80/usermovierating/getbyuseridmovieid?user_id=' + localStorage.getItem("ACCESS_TOKEN") + '&movie_id=' + this.props.movie_id)
        .then(res => {
          this.setState({
            rating: res.data.rating,
          });
        }).catch(error => {
          this.setState({
            rating: 0,
          });
        });
      }else{
        this.setState({
          rating: 0,
        });
      }
  }

  onStarClick(nextValue, prevValue, name) {
    if (localStorage.getItem("isAuthenticated") === null) {
      history.push("/login");
    }else if(localStorage.getItem('isAuthenticated') == "true"){
        this.setState({rating: nextValue});
        var movieData = {};
        movieData["userId"] = localStorage.getItem("ACCESS_TOKEN");
        movieData["movieId"] = this.props.movie_id;
        movieData["movieName"] = this.props.movie_name;
        movieData["rating"] = nextValue;
        axios.post('http://149.165.169.90:80/usermovierating/save',  movieData )
            .then(res => {
              Alert.success("Rating saved successfully");
            }).catch(error => {
              Alert.error("Sorry! Some error occurred.");
            });
      }else{
        history.push('/login');
      }
  }

  render() {
    const { rating } = this.state;

    return (
      <div>
        <StarRatingComponent
          name="rate1"
          starCount={10}
          value={rating}
          onStarClick={this.onStarClick.bind(this)}
        />
      </div>
    );
  }
}

export default StarRating;
