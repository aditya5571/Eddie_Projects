import axios from "axios";
import {GET_ERRORS, GET_SELECTED_ADS, SET_CURRENT_USER} from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";


export const createNewUser = (newUser, history) => async dispatch => {

    try{
        await axios.post("http://localhost:8080/api/users/register", newUser);
        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
        // Ensure publisher and shop owners they can't log in yet.
        if (newUser.userType == "Publisher" || newUser.userType == "Shop owner") {
            alert("Request is sent in! Please wait for your admin approval email before you log in")
        }
    }
    catch (err){
        dispatch ({
            type: GET_ERRORS,
            payload: err.response.data
        });

    }

};

export const login = LoginRequest => async dispatch => {
    try {
        // post => Login Request
        const res = await axios.post("http://localhost:8080/api/users/login", LoginRequest);
        // extract token from res.data
        const { token } = res.data;
        // store the token in the localStorage
        localStorage.setItem("jwtToken", token);
        // set our token in header ***
        setJWTToken(token);
        // decode token on React
        const decoded = jwt_decode(token);
        // dispatch to our securityReducer
        dispatch({
            type: SET_CURRENT_USER,
            payload: decoded
        });


    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const logout = () => dispatch => {
    localStorage.removeItem("jwtToken");
    setJWTToken(false);
    dispatch({
        type: SET_CURRENT_USER,
        payload: null
    });
};