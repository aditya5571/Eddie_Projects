import React, { Component } from "react";
import { link, Link } from "react-router-dom";


class BookCard extends Component {
  render() {
    return (
      <div className="book">
          <React.Fragment>
            <Link to={`/bookPage/${this.props.book.isbn}`} className="book-title-text">
              <h5>
                <img
                    className="img-class"
                    src={this.props.book.image}
                    alt="Image not found"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = "/default.jpg";
                    }}
                  />
              </h5>
            </Link>
          </React.Fragment>
      </div>
    );
  }
}

export default BookCard;


// {this.props.book.name} <br />


// <a className="book-title-text" href="/bookPage">
// <h5>
//   {this.props.arr.name} <br />
// </h5>
// </a>

// {this.state.allBooks.map((key) => (
//   <div key={key.isbn}>
// <h5>
//   Book name: {key.name} <br />
//   Book author: {key.author} <br />
//   Book category: {key.category} <br />
//   Book ISBN: {key.isbn} <br />
// </h5>
//   </div>
// ))}
