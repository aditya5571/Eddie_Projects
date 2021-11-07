import {
    GET_USER_TRANSACTIONS,
    GET_ALL_TRANSACTIONS,
    GET_ALL_BOOK_TRANSACTIONS,
    GET_CART_ITEMS,
    GET_CART_TOTAL
} from "../actions/types";

export const initialState = {
    userTransactions: [],
    allTransactions: [],
    purchasedBooks: [],
    cartItems: [],
    cartTotal: 0
};

export default function(state = initialState, action) {
    switch (action.type) {
        case GET_USER_TRANSACTIONS:
            return {
                ...state,
                userTransactions: action.payload
            };
        case GET_ALL_TRANSACTIONS:
            return {
                ...state,
                allTransactions: action.payload
            };
        case GET_ALL_BOOK_TRANSACTIONS:
            return {
                ...state,
                purchasedBooks: action.payload
            };
        case GET_CART_ITEMS:

            return {
                ...state,
                cartItems: action.payload
            }
        case GET_CART_TOTAL:
            return{
                ...state,
                cartTotal: action.payload

            }
        default:
            return state;
    }
}
