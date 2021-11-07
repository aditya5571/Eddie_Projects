import React from 'react'
import {link, Link} from "react-router-dom";

 const CreateBookButton=() => {
    return (
        <React.Fragment>
        <Link to="/addBook"
        className="btn btn-lg btn-info">
        Create a Book
        </Link>
        </React.Fragment>
    )
};
export default CreateBookButton;