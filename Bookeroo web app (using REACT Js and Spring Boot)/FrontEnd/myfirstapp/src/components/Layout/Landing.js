import React, { Component } from "react";
import { Link } from "react-router-dom";

class Landing extends Component {
  render() {
    return (
      <div className="landing">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-12 text-center">
                <h1 className="display-3 mb-4">
                  BOOKEROO
                </h1>
                <br></br>
                <h2>
                  Please sign up/log in or checkout our the book catalog
                </h2>
                <hr />
                <Link className="btn btn-lg btn-primary mr-2" to="/register">
                  Sign Up
                </Link>
                <Link className="btn btn-lg btn-secondary mr-2" to="/login">
                  Login
                </Link>
                <Link className="btn btn-lg btn-secondary mr-2" to="/bookCatalog">
                  Book Catalog
                </Link>

              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Landing;