import React from 'react'
import {Link} from "react-router-dom";

const CreateAllTransactionsButton=() => {
    return (
        <React.Fragment>
            <Link to="/order/all"
                  className="btn btn-lg btn-info">
                All Orders
            </Link>
        </React.Fragment>
    )
};
export default CreateAllTransactionsButton;