import React, { Component } from "react";
import "./App.css";
import Dashboard from "./components/Dashboard";
import Header from "./components/Layout/Header";
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import AddPerson from "./components/Persons/AddPerson";
import { Provider } from "react-redux";
import store from "./store";

import Landing from "./components/Layout/Landing";
import Register from "./components/UserManagement/Register";
import Login from "./components/UserManagement/Login";

import AboutUs from "./staticPage/AboutUs";
import ContactUs from "./staticPage/ContactUs";
import Footer from "./components/Layout/Footer";

import BookCatalog from "./components/BookManagement/BookCatalog";
import BookPage from "./components/BookManagement/BookPage";
import ManageBooks from "./components/Books/ManageBooks";
import AddBook from "./components/Books/AddBook";
import AddReview from "./components/Books/AddReview"
import AdminOrders from "./components/UserManagement/AdminOrders";
import jwt_decode from "jwt-decode";
import setJWTToken from "./securityUtils/setJWTToken";
import { SET_CURRENT_USER } from "./actions/types";
import { logout } from "./actions/securityActions";
import SecuredRoute from "./securityUtils/SecureRoute";
import SearchResults from "./components/BookManagement/SearchResults";
import Requests from "./components/UserManagement/Requests";

import AdminAllUsers from "./components/UserManagement/AdminAllUsers";
import AllBooks from "./components/UserManagement/AllBooks";

import Sellers from "./components/BookManagement/Sellers";
import PostAd from "./components/BookManagement/PostAd";
import CheckOut from "./components/Order/CheckOut";
import UserOrders from "./components/UserManagement/UserOrders";
import Cart from "./components/Order/Cart";

const jwtToken = localStorage.jwtToken;

if (jwtToken) {
  setJWTToken(jwtToken);
  const decoded_jwtToken = jwt_decode(jwtToken);
  store.dispatch({
    type: SET_CURRENT_USER,
    payload: decoded_jwtToken
  });

  const currentTime = Date.now() / 1000;
  if (decoded_jwtToken.exp < currentTime) {
    store.dispatch(logout());
    window.location.href = "/";
  }
}

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <Router>
          <div className="App">
            <Route component={Header} />
            {
              //Public Routes
            }
           
            <Route exact path="/">
              <Redirect to="bookCatalog" />
            </Route>
            <Route exact path="/register" component={Register} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/aboutUs" component={AboutUs} />
            <Route exact path="/contactUs" component={ContactUs} />

            <Route exact path="/bookCatalog" component={BookCatalog} />
            <Route exact path="/bookPage/:isbn" component={BookPage} />
            <Route exact path="/manageBooks" component={ManageBooks} />

            <Route exact path="/search-results" component={SearchResults}/>
            <Route exact path="/bookPage/:isbn/sellers" component={Sellers}/>

            <Route exact path="/addReview" render={props => (
                <AddReview {...props}/>
            )}>
            </Route>
            {
              //Private Routes
            }
            
            <SecuredRoute exact path="/dashboard" component={Dashboard} />
            <SecuredRoute exact path="/newAd" component={PostAd} />

            <SecuredRoute exact path="/addBook" component={AddBook}/>
            <SecuredRoute exact path="/addPerson" component={AddPerson} />
            <SecuredRoute exact path="/checkout" component={CheckOut} />
            <SecuredRoute exact path="/requests" component={Requests} />
            <SecuredRoute exact path="/cart" component={Cart} />
            <SecuredRoute exact path="/all-books" component={AllBooks}/>
            <SecuredRoute exact path="/orders/all" component={AdminOrders} />

            <SecuredRoute exact path="/book/all" component={AllBooks}/>

            <SecuredRoute exact path="/user/requests" component={Requests} />

            <SecuredRoute exact path="/order" component={UserOrders} />
            <SecuredRoute exact path="/order/all" component={AdminOrders} />
            <SecuredRoute exact path="/user/all" component={AdminAllUsers} />




          </div>
          <Footer /> 
        </Router>
      </Provider>
    );
  }
}
export default App;