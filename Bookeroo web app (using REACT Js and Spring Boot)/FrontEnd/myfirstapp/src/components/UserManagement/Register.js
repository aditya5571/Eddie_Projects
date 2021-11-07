import React, { Component } from "react";
import { createNewUser } from "../../actions/securityActions";
import * as PropTypes from 'prop-types'
import { connect } from "react-redux";
import classnames from "classnames";

class Register extends Component {
  constructor() {
    super();

    this.state = {
      username: "",
      fullName: "",
      password: "",
      confirmPassword: "",
      phoneNumber: "",
      shopName:"",
      address: "",
      abnNumber: "",
      userType: "",
      showABN: false,
      showShopName: false,

      errors: {}
    };
    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  // Hides the shop name and abn for normal customers to cause less confusion
  chooseUser=(e)=> {
    const value = e.target.value;
    if(value == "Customer") {
      this.setState({showABN: false, showShopName: false})
    } else if(value == "Publisher") {
      this.setState({showABN: true, showShopName: true})
    } else if(value == "Shop owner") {
      this.setState({showABN: true, showShopName: true})
    }
  }

  onChange(e) {
    this.setState({
      [e.target.name]: e.target.value
    });

    if(e.target.name ==="userType"){
      this.chooseUser(e)

    }
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {

      this.setState({ errors: nextProps.errors });
    }
  }

  onSubmit(e) {
    e.preventDefault();
    const newUser = {
      username: this.state.username,
      fullName: this.state.fullName,
      password: this.state.password,
      confirmPassword: this.state.confirmPassword,
      phoneNumber: this.state.phoneNumber,
      shopName: this.state.shopName,
      abnNumber: this.state.abnNumber,
      address : this.state.address,
      userType: this.state.userType
    };

    this.props.createNewUser(newUser, this.props.history);
  }

  render() {
    const { errors } = this.state;
    return (
        <div className="register">
          <div className="container">
            <div className="row">
              <div className="col-md-8 m-auto">
                <h1 className="display-4 text-center">Sign Up</h1>
                <p className="lead text-center">Create your Account</p>
                {Object.keys(errors).map(key => {
                  return <div>{errors[key]}</div>
                })}
                <p className="display-6"> User Type </p>
                <form onSubmit={this.onSubmit}>
                  <div className="form-group" onChange={this.onChange}>
                    <label> &nbsp;Customer &nbsp; </label>
                    <input
                        type="radio"
                        name="userType"
                        value="Customer"
                        // onChange={this.onChange}
                    />
                    <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Publisher &nbsp;</label>
                    <input
                        type="radio"
                        name="userType"
                        value="Publisher"
                        // onChange={this.onChange}
                    />
                    <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Shop Owner &nbsp; </label>
                    <input
                        type="radio"
                        name="userType"
                        value="Shop owner"
                        // onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="text"
                        className="form-control form-control-lg"
                        placeholder="Full Name"
                        name="fullName"
                        value={this.state.fullName}
                        onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="email"
                        className="form-control form-control-lg"
                        placeholder="Email Address (Username)"
                        name="username"
                        value={this.state.username}
                        onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    {this.state.showABN && <input
                        required={true}
                        type="number"
                        className="form-control form-control-lg"
                        placeholder="ABN Number"
                        name="abnNumber"
                        value={this.state.abnNumber}
                        onChange={this.onChange}
                    />}
                  </div>
                  <div className="form-group">
                    {this.state.showShopName && <input
                        required={true}
                        type="text"
                        className="form-control form-control-lg"
                        placeholder="Shop Name"
                        name="shopName"
                        value={this.state.shopName}
                        onChange={this.onChange}
                    />}
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="number"
                        className="form-control form-control-lg"
                        placeholder="Phone Number"
                        name="phoneNumber"
                        value={this.state.phoneNumber}
                        onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="text"
                        className="form-control form-control-lg"
                        placeholder="Address "
                        name="address"
                        value={this.state.address}
                        onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="password"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.password     })}
                        placeholder="Password"
                        name="password"
                        value={this.state.password}
                        onChange={this.onChange}
                    />
                  </div>
                  <div className="form-group">
                    <input
                        required={true}
                        type="password"
                        className={classnames("form-control form-control-lg", {
                          "is-invalid": errors.password   })}
                        placeholder="Confirm Password"
                        name="confirmPassword"
                        value={this.state.confirmPassword}
                        onChange={this.onChange}
                    />
                  </div>
                  <input type="submit" className="btn btn-info btn-block mt-4" />
                </form>
              </div>
            </div>
          </div>
        </div>
    );
  }
}

Register.propTypes = {
  createNewUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  errors: state.errors
});
export default connect(
    mapStateToProps,
    { createNewUser }
)(Register);