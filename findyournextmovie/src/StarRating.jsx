import React from 'react';
import ReactDOM from 'react-dom';
import StarRatingComponent from 'react-star-rating-component';
import axios from 'axios';

class StarRating extends React.Component {
  constructor() {
    super();

    this.state = {
      rating: '',
      movieId: '',
    };
  }

  componentWillMount() {
    this.setState({
      movieId: this.props.movie_id,
    });

    axios.get('http://localhost:8081/usermovierating/getbyuseridmovieid?user_id=' +  this.props.movie_id + '&movie_id=' + this.props.movie_id)
    .then(res => {
      console.log('res json :: ' + JSON.stringify(res));
      this.setState({
        rating: res,
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
    var movieData = {};
    movieData["userId"] = '123';
    movieData["movieId"] = this.props.movie_id;
    movieData["movieName"] = this.props.movie_name;
    movieData["rating"] = this.state.rating;

    axios.post('http://localhost:8081/usermovierating/save',  movieData )
        .then(res => {
          console.log(res);
          console.log(res.data);
        }).catch(error => {
          console.log('error!!');
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
