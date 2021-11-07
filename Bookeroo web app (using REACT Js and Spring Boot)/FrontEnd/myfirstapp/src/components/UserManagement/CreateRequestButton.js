import React from 'react'
import {Link} from "react-router-dom";

const CreateRequestButton=() => {
    return (
        <React.Fragment>
            <Link to="/user/requests"
                  className="btn btn-lg btn-info">
                User Requests
            </Link>
        </React.Fragment>
    )
};
export default CreateRequestButton;