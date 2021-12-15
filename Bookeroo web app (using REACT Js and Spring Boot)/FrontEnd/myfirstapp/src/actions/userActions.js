import axios from "axios";
import {GET_USER_REQUESTS, GET_ALL_USERS, GET_ERRORS} from "./types";

// Retrieves all the unapproved shop owners and publishers from the backend
export const getAllUnapprovedUsers = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/users/requests", );
    dispatch({
        type: GET_USER_REQUESTS,
        payload: res.data
    });
};

// Retrieves all the users from the backend. To be implemented.
export const getAllUsers = () => async dispatch => {
    const res = await axios.get("http://localhost:8080/api/users/all", );
    dispatch({
        type: GET_ALL_USERS,
        payload: res.data
    });
};

// Sends an update to the backend to approve the user
export const approveUser = (id) => async dispatch => {
    try {
        // Returns a list of unapproved users for redux
        const res = await axios.post("http://localhost:8080/api/users/requests", id);
        dispatch({
            type: GET_USER_REQUESTS,
            payload: res.data
        });
    } catch (err) {
        dispatch ({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

// Sends an update to the backend to ban the user
export const banUser = (id) => async dispatch => {
    try {
        await axios.post("http://localhost:8080/api/users/ban", id);
        const res = await axios.get("http://localhost:8080/api/users/all", );
        const res2 = await axios.get("http://localhost:8080/api/users/requests", );
        dispatch({ type: GET_ALL_USERS, payload: res.data});
        dispatch({ type: GET_USER_REQUESTS, payload: res2.data});
    } catch (err) {
        dispatch ({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};

export const refundOrder = (id) => async dispatch => {
    try {
        const res = await axios.post("http://localhost:8083/api/orders/refund", id);
        dispatch({
            // Additional implementation needs to added for overall users
            // For now, it is just publishers/shop owners being banned, so we can leave this for now
            type: GET_USER_REQUESTS,
            payload: res.data
        });
    } catch (err) {
        dispatch ({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};