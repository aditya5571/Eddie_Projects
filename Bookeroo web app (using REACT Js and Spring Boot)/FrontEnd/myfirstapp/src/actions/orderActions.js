import axios from "axios";
import {
  GET_ERRORS,
  GET_ALL_TRANSACTIONS,
  GET_USER_TRANSACTIONS,
  GET_ALL_BOOK_TRANSACTIONS,
  GET_CART_ITEMS, GET_BOOKS, GET_CART_TOTAL
} from "./types";
import {forEach} from "react-bootstrap/ElementChildren";
import {getBook} from "./bookActions";

export const getUserTransactions = (userId) => async dispatch => {
  try{
    const res = await axios.get(`http://localhost:8084/api/orders/user/${userId}`);
    console.log(res);
    dispatch({
      type: GET_USER_TRANSACTIONS,
      payload: res.data
    });
  }
  catch (err){
    dispatch ({
      type: GET_ERRORS,
      payload: err.response.data
    });

  }
};

export const getAllTransactions = () => async dispatch => {
  try{
    const res = await axios.get("http://localhost:8084/api/orders/user/all");
    dispatch({
      type: GET_ALL_TRANSACTIONS,
      payload: res.data
    });
  }
  catch (err){
    dispatch ({
      type: GET_ERRORS,
      payload: err.response.data
    });

  }
};

export const refundOrder = (orderId) => async dispatch => {
  try{
    const res = await axios.post("http://localhost:8084/api/orders/refund", orderId);
    alert(res.data);
    window.location.reload(false);

  }
  catch (err){
    alert("Error: Order cannot be found in our database. Has it already been completed or cancelled?")
  }
};


export const getShoppingCart = () => async dispatch => {
  try{
    const res = await axios.get("http://localhost:8084/api/orders/cartItems")
    var itemIds = res.data
    var ads = []
    var total = 0;
    for(var i =0; i<itemIds.length; i++ ){
      const ad = await axios.get("http://localhost:8081/api/books/ads/" + itemIds[i])
      ads.push(ad.data)
      total+=ad.data.price
    }
    dispatch({
      type: GET_CART_ITEMS,
      payload: ads
    });
    dispatch({
      type: GET_CART_TOTAL,
      payload: total
    });

  }
  catch (err){
    dispatch ({
      type: GET_ERRORS,
      payload: err.response.data
    });

  }
};

