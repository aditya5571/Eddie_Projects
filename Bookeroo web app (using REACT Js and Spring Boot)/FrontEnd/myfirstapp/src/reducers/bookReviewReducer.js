import { GET_REVIEWS, GET_USER } from "../actions/types";

export const initialState = {
  bookReviews: []
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_REVIEWS:
      return {
        ...state,
        bookReviews: action.payload
      };

    default:
      return state;
  }
}
