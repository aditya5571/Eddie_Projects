import React, { Component } from "react";
import { search } from "../../actions/bookActions";
import { connect } from "react-redux";
import * as PropTypes from "prop-types";
import {AiOutlineShoppingCart} from "react-icons/all";

class Header extends Component {
  constructor() {
    super();
    this.state = {
      searchFor: "",
      searchBy: "name"
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onSubmit(e) {
    e.preventDefault();
    const searchRequest = {
      searchFor: this.state.searchFor,
      searchBy: this.state.searchBy
    };
    this.props.history.push("/search-results");
    this.props.search(searchRequest);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }
  render() {
    return (
      <div>
        <nav className="navbar navbar-expand-sm navbar-dark bookeroo-nav mb-2">
          <div className="container">
            <a className="navbar-brand" href="/bookCatalog">
              BOOKEROO
            </a>

            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <a className="nav-link" href="/dashboard">
                  Dashboard
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link " href="/manageBooks">
                  Manage Books
                </a>
              </li>
            </ul>
            <form className="form-inline my-2 my-lg-0" onSubmit={this.onSubmit}>
              <select name="searchBy" className="form-control" onChange={this.onChange}>
                <option value="name">By: Name</option>
                <option value="isbn">By: Isbn</option>
                <option value="author">By: Author</option>

              </select>
              <input
                type="text"
                className="form-control mr-sm"
                name="searchFor"
                value={this.state.searchFor}
                onChange={this.onChange}
                required={true}
              />
              <button type="submit" className="btn btn-success">
                <i className="fas fa-search"></i>
              </button>
            </form>
            <ul className="navbar-nav ml-auto">


              {this.props.security.validToken && <li className="nav-item">
                <a className="nav-link" href="/cart">
                  <AiOutlineShoppingCart size={"25px"}/>
                </a>
              </li>}
              {/* Changes the sign up/login button to user's full name if the user is logged in. */}
              {!this.props.security.validToken ? (
                <>
                  <li className="nav-item">
                    <a className="nav-link " href="/register">
                      Sign Up
                    </a>
                  </li>
                  <li className="nav-item">
                    <a className="nav-link" href="/login">
                      Login
                    </a>
                  </li>
                </>
              ) : (
                <li className="nav-item">
                  <a className="nav-link" href="/dashboard">
                    Welcome {this.props.security.user.fullName}!
                  </a>
                </li>
              )}

            </ul>
          </div>
        </nav>
      </div>
    );
  }
}

// For the conditional rendering of the sign up and login buttons to user
Header.propTypes = {
  security: PropTypes.object.isRequired,
};
const mapStateToProps = (state) => ({
  security: state.security,
});

export default connect(mapStateToProps, { search })(Header);
