import {GET_BOOK, GET_BOOKS, GET_CATEGORIES} from "../actions/types";

export const initialState = {
    books: [],
    allCategories: [],
    book: []
};

export default function(state = initialState, action) {
    switch (action.type) {
        case GET_BOOKS:
            return {
                ...state,
                books: action.payload
            };
        case GET_CATEGORIES:
            return {
                ...state,
                allCategories: action.payload
            };
        case GET_BOOK:
            return {
                ...state,
                book: action.payload
            }

        default:
            return state;
    }
}
