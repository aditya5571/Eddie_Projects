import React from 'react'
import {Link} from "react-router-dom";

const CreateAllUsersButton=() => {
    return (
        <React.Fragment>
            <Link to="/user/all"
                  className="btn btn-lg btn-info">
                All Users
            </Link>
        </React.Fragment>
    )
};
export default CreateAllUsersButton;