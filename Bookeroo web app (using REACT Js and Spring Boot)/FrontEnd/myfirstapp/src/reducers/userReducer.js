import { GET_USER_REQUESTS, GET_ALL_USERS } from "../actions/types";

export const initialState = {
    userRequests: [],
    allUsers: []
};

export default function(state = initialState, action) {
    switch (action.type) {
        case GET_USER_REQUESTS:

            return {
                ...state,
                userRequests: action.payload
            };
        case GET_ALL_USERS:

            return {
                ...state,
                allUsers: action.payload
            };
        default:
            return state;
    }
}
