import React, {Component} from 'react'
import PropTypes from "prop-types";
import {connect} from "react-redux";
import PayPalCheckout from "./PayPalCheckout";
import {getShoppingCart} from "../../actions/orderActions";
import classnames from "classnames";
import CartItem from "./CartItem";
import {Link} from "react-router-dom";


class CheckOut extends Component {
    constructor() {
        super();

        this.state = {
            address_line_1: "",
            address_line_2: "",
            admin_area_2: "", // city
            admin_area_1: "Australian Capital Territory", // state
            postal_code: "",
            country_code: "AU",
            errors: {}
        };
        this.onChange = this.onChange.bind(this);
        this.onRemove = this.onRemove.bind(this);
    }

    componentDidMount() {
        this.props.getShoppingCart()
    }

    onRemove() {
        this.props.getShoppingCart()
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
            this.setState({
                errors: nextProps.errors
            });

        }
    }

    onChange(e) {
        this.setState({[e.target.name]: e.target.value});
    }

    render() {
        const {errors} = this.state;
        return (
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h2 className="display-5 text-center">Shipping Information</h2>
                        <br/>
                        <div className="form-group">
                            <input
                                type="text"
                                className={classnames("form-control form-control-lg", {
                                    "is-invalid": errors.name
                                })}
                                placeholder="Address Line 1"
                                name="address_line_1"
                                value={this.state.address_line_1}
                                onChange={this.onChange}
                                required
                            />
                            {errors.name && (
                                <div className="invalid-feedback">{errors.name}</div>
                            )}
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control form-control-lg"
                                placeholder="Address Line 2"
                                name="address_line_2"
                                value={this.state.address_line_2}
                                onChange={this.onChange}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control form-control-lg"
                                placeholder="City"
                                name="admin_area_2"
                                value={this.state.admin_area_2}
                                onChange={this.onChange}
                            />
                        </div>
                        <div className="form-group">
                            <select defaultValue={this.state.admin_area_1}
                                    onChange={this.onChange}
                                    className="form-control form-control-lg"
                            >
                                <option value="ACT">Australian Capital Territory</option>
                                <option value="NSW">New South Wales</option>
                                <option value="NT">Northern Territory</option>
                                <option value="QLD">Queensland</option>
                                <option value="SA">South Australia</option>
                                <option value="VIC">Victoria</option>
                                <option value="WA">Western Australia</option>
                            </select>
                        </div>
                        <div className="form-group">
                            <input
                                type="number"
                                className="form-control form-control-lg"
                                placeholder="Postcode"
                                name="postal_code"
                                value={this.state.postal_code}
                                onChange={this.onChange}
                            />
                        </div>
                        <div className="form-group">
                            <input
                                type="text"
                                className="form-control form-control-lg"
                                placeholder="Postcode"
                                name="country_code"
                                value={"AU"}
                                onChange={this.onChange}
                            />
                        </div>
                        <br/>
                        <PayPalCheckout checkout={this.props} address={this.state}/>
                    </div>
                    <div className="col">
                        <h2 className="display-5 text-center">Order Information</h2>
                        <div>{this.props.cartItems.length > 0 ?
                            (<div>
                                {this.props.cartItems.map((item) => {
                                    return <CartItem item={item} key={item.id} onRemove={this.onRemove}/>
                                })}
                                <hr/>
                                <div>Total Cost: ${this.props.cartTotal}</div>
                                <hr/>
                            </div>) : (
                                <div style={{textAlign: "center"}}>
                                    <h3>Order empty!</h3>
                                </div>
                            )
                        }</div>
                    </div>
                </div>
            </div>
        );
    }
}

CheckOut.propTypes = {
    createProject: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired,
    cartItems: PropTypes.object.isRequired,
    cartTotal: PropTypes.object.isRequired
};

const mapStateToProps = (state) => {
    return {
        security: state.security,
        cartItems: state.order.cartItems,
        cartTotal: state.order.cartTotal,
        errors: state.errors
    }
};

export default connect(
    mapStateToProps,
    {getShoppingCart}
)(CheckOut);
