import axios from "axios";
import { GET_ERRORS } from "./types";

export const createReview = (review, history) => async dispatch => {
    try {
        const res = await axios.post(`http://localhost:8080/api/reviews/create`)
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
};