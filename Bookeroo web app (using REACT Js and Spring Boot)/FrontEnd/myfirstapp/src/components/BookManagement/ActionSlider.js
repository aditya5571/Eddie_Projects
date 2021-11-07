import React, { Component } from "react";
import { connect } from "react-redux";
import BookCard from "./BookCard";
import bookCatalogActions from "../../actions/bookCatalogActions";
import {getAllBooks} from "../../actions/bookActions";

class ActionSlider extends Component {
  constructor() {
    super();

    this.state = {
      allBooks: [],
      book: [],
    };
  }

  // componentDidMount(res) {
    // this.props.getAllBooks().then((res) => {
    //   this.setState({ allBooks: res.data });
    // });
  // }

  // componentDidMount(res) {
  //   bookCatalogActions.getAllBooks().then((res) => {
  //     this.setState({ allBooks: res.data });
  //   });
  // }

  render() {
    return (
      <div className="bookCatalog">
        <div className="sliderContainer">
          {this.props.allBooks.map((book) => (
            <div key={book.isbn}>
              <BookCard book={book} />
            </div>
          ))}
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

export default connect(
  mapStateToProps,
  { getAllBooks }
)(ActionSlider);

// export default ActionSlider;

// {this.state.allBooks.map((book) => (
//   <div key={book.isbn}>
//     <BookCard arr={book} />
//   </div>
// ))}

// <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
//           <div className="book">Item</div>
