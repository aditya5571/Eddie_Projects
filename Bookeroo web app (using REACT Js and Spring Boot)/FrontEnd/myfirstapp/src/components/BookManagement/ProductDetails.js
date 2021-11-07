import React, { Component } from "react";
import {Link} from "react-router-dom";

class ProductDetails extends Component {
  render() {
    return (
      <div className="bookCatalog">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-4">
                <div className="book-cover-image-container">
                  <img
                    src={this.props.book.image}
                    alt="Image not found"
                    onError={(e) => {
                      e.target.onerror = null;
                      e.target.src = "/default.jpg";
                    }}
                  />
                </div>
              </div>
              <div className="col-md-8">
                <h1 className="book-title">{this.props.book.name}</h1>
                <h4 className="book-author">{this.props.book.author}</h4> <br/><br/>
                <div className="book-desc">{this.props.book.description}</div><br/><br/><br/>
                <h4 className="book-author">{this.props.book.category}</h4> <br/>
                <br/><br/><br/><br/>
                <div className="seller">
                  <Link className="btn btn-lg btn-primary mr-2" to={window.location.pathname+"/sellers"}>
                    Choose Seller
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ProductDetails;


// <div className="col-md-4 text-align-center">
//   <h4>Price: ${this.props.book.price}</h4>
// </div>;


// <br /> <br />
// <div className="row>">
  
// </div>
// <br /> <hr />