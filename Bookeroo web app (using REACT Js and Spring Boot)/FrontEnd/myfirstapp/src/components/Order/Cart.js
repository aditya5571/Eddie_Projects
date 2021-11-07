import React, { Component } from 'react'
import axios from "axios";
import CartItem from "./CartItem";
import {connect} from "react-redux";
import {getSellers, getShoppingCart, saveOrder} from "../../actions/orderActions";
import {Link} from "react-router-dom";

class Cart extends Component{
    constructor(props) {
        super(props);
        this.state ={
            updated: false
        }
        this.onRemove = this.onRemove.bind(this)
    }

    componentDidMount() {
        this.props.getShoppingCart()
    }

    onRemove(){
        window.location.reload()
    }

    render() {
        return(
            <div>{this.props.cartItems.length>0 ?
       ( <div>
            {this.props.cartItems.map((item) => {
                return <CartItem item={item} key={item.id} onRemove={this.onRemove}/>
            })}
           <hr/>
            <div>Total Cost: ${this.props.cartTotal}</div>
           <hr/>
           <div className="text-center">
               <Link className="btn btn-lg btn-success mr-2" to="/checkout">
                   Checkout
               </Link>
           </div>
        </div>) :(
            <div style={{textAlign:"center"}}>
                <h3>Your Cart is empty ! Go get shopping :)</h3>
            </div>
                    )
            }</div>
        )
    }

}

const mapStateToProps = (state) => {
    return{
        cartItems: state.order.cartItems,
        cartTotal: state.order.cartTotal
    }
};

export default connect(
    mapStateToProps,
    {getShoppingCart }
)(Cart);
