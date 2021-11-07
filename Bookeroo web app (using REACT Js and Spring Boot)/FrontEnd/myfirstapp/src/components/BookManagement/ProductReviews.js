import React, { Component } from "react";

class ProductReviews extends Component {
  render() {
    return (
      <div className="bookCatalog">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <h1 className="book-review">Reviews & Ratings</h1>
              {this.props.reviews.map((review) => (
                <div key={review.id}>
                  <br />
                  <h5>{review.userName}</h5>
                  <p>
                    {review.rating} <span className="icon">★</span><br/>
                    {review.review}
                  </p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProductReviews;
