import React from 'react';
import ReactDOM from 'react-dom';
import StarRatingComponent from 'react-star-rating-component';
import axios from 'axios';
import Alert from "react-s-alert";

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

    axios.get('http://localhost:8081/usermovierating/getbyuseridmovieid?user_id=' +  '123' + '&movie_id=' + this.props.movie_id)
    .then(res => {
      console.log('res json :: ' + JSON.stringify(res));
      this.setState({
        rating: res.data.rating,
      });
    }).catch(error => {
      this.setState({
        rating: 0,
      });
      console.log('componentWillMount error!!');
    });
  }

  onStarClick(nextValue, prevValue, name) {
    this.setState({rating: nextValue});
    console.log('rating :: ' + this.state.rating + nextValue);
    var movieData = {};
    movieData["userId"] = '123';
    movieData["movieId"] = this.props.movie_id;
    movieData["movieName"] = this.props.movie_name;
    movieData["rating"] = nextValue;
    axios.post('http://localhost:8081/usermovierating/save',  movieData )
        .then(res => {
          Alert.success("Rating saved successfully");
        }).catch(error => {
          Alert.error("Some error occurred");
        });
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
