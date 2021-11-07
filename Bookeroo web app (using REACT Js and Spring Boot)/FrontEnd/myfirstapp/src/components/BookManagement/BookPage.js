import React, { Component } from "react";
import { connect } from "react-redux";
import ProductDetails from "./ProductDetails";
import ProductReviews from "./ProductReviews";
import AddReview from "../Books/AddReview";
import { getBook, getBookReviews } from "../../actions/bookActions";

class BookPage extends Component {
  componentDidMount() {
    const bookISBN = this.props.match.params.isbn;
    this.props.getBook(bookISBN);
    this.props.getBookReviews(bookISBN);
  }

  render() {
    return (
      <div className="bookCatalog">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <ProductDetails book={this.props.book} />
              <br/><hr/>
              <ProductReviews reviews={this.props.reviews} />
              <br/>
              <AddReview/>
            </div>
            <br/>
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    book: state.book.book,
    reviews: state.review.bookReviews,
  };
};

export default connect(mapStateToProps, { getBook, getBookReviews })(BookPage);
