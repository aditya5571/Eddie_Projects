import React, { Component } from "react";
// import BookCarousal from "./BookCarousal";
import { connect } from "react-redux";
import { login } from "../../actions/securityActions";
import { getAllBooks } from "../../actions/bookActions";
import ActionSlider from "./ActionSlider";
import BookCarousal from "./BookCarousal";

class BookCatalog extends Component {
  componentDidMount() {
    this.props.getAllBooks();
  }

  render() {
    return (
      <div className="bookCatalog">
        <div className="bookeroo-container">
          <div className="bookeroo-logo col-md-12 text-center">
            <img
              className="img-class"
              src="https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/Bookeroo-logo.png"
              alt="Image not found"
              onError={(e) => {
                e.target.onerror = null;
                e.target.src = "/default.jpg";
              }}
            />
            <h1 className="bookeroo-font-title">Bookeroo Studios</h1>
          </div>
          <br />
          <hr />
          <div className="row">
            <div className="col-md-4 align-left">
              <img
                className="img-class-thriller"
                src="https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/thriller_zone.jpg"
                alt="Image not found"
                onError={(e) => {
                  e.target.onerror = null;
                  e.target.src = "/default.jpg";
                }}
              />
            </div>
            <div className="col-md-2"></div>
            <div className="col-md-5">
                <br/><br/><br/><br/><br/><br/><br/><br/>
                <div className="bookeroo-font-category-title">
                  Browse Our Latest Thriller Novels & More
                </div>
            </div>
            <br/>
          </div>
          <div className="bookeroo-books">
            <BookCarousal books={this.props.books} />
          </div>
        </div>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    books: state.book.books,
  };
};

export default connect(mapStateToProps, { getAllBooks })(BookCatalog);

// <h3 className="bookeroo-font-category-title">Thriller</h3>
// display-3 mb-4
