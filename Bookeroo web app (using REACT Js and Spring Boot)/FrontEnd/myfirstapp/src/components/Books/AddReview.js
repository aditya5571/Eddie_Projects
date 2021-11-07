import React, { Component } from "react";
import { connect } from "react-redux";
import "./AddReview.css";
import { addNewReview } from "../../actions/bookActions";

class AddReview extends Component {
  constructor() {
    super();

    this.state = {
      userName: "",
      review: "",
      rating: "",
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  onSubmit(e) {
    e.preventDefault();
    const url = window.location.pathname;
    const bookISBN = url.substring(url.lastIndexOf("/") + 1);
    const newReview = {
      userName: this.state.userName,
      bookISBN: bookISBN,
      review: this.state.review,
      rating: this.state.rating,
    };
    this.props.addNewReview(newReview);
    window.location.reload(false);
  }

  render() {
    return (
      <form className="review-form" onSubmit={this.onSubmit}>
        <div className="review-container">
          <label>Write your review:</label>
          <div className="rating-container">
            <label>
              <input
                type="radio"
                name="rating"
                value="1"
                onChange={this.onChange}
                required
              />
              <span className="icon">★</span>
            </label>
            <label>
              <input
                type="radio"
                name="rating"
                value="2"
                onChange={this.onChange}
              />
              <span className="icon">★</span>
              <span className="icon">★</span>
            </label>
            <label>
              <input
                type="radio"
                name="rating"
                value="3"
                onChange={this.onChange}
              />
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
            </label>
            <label>
              <input
                type="radio"
                name="rating"
                value="4"
                onChange={this.onChange}
              />
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
            </label>
            <label>
              <input
                type="radio"
                name="rating"
                value="5"
                onChange={this.onChange}
              />
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
              <span className="icon">★</span>
            </label>
          </div>
          <input
            type="text"
            placeholder="Full Name"
            name="userName"
            value={this.state.userName}
            onChange={this.onChange}
          />
          <br />
          <br />
          <textarea
            id="review"
            name="review"
            value={this.state.review}
            onChange={this.onChange}
            placeholder="Write your review here..."
            rows="4"
            cols="80"
            required
          />
          <hr />
          <button type="submit" className="review-btn">
            Submit Review
          </button>
        </div>
      </form>
    );
  }
}
const mapStateToProps = (state) => {
  return {};
};

export default connect(mapStateToProps, { addNewReview })(AddReview);
