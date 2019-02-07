import React from 'react';
import ReactDOM from 'react-dom';
import StarRatingComponent from 'react-star-rating-component';

class NonEditableStarRating extends React.Component {
  constructor() {
    super();

    this.state = {
      rating: 0,
    };
  }
  componentWillMount() {
    this.setState({
      rating: this.props.rating,
    });
  }
  render() {

    return (
      <div>
        <StarRatingComponent
          name="rate2"
          editing={false}
          starCount={10}
          value={this.state.rating}
        />
      </div>
    );
  }
}

export default NonEditableStarRating;
