import React, { Component } from 'react'
import { connect } from "react-redux";
import {logout} from "../actions/securityActions";
import CreateRequestButton from "./UserManagement/CreateRequestButton";
import PropTypes from "prop-types";
import {Link} from "react-router-dom";
import CreateAllBooksButton from "./UserManagement/CreateAllBooksButton";
import CreateUserOrderButton from "./UserManagement/CreateUserOrderButton";
import CreateAllTransactionsButton from "./UserManagement/CreateAllTransactionsButton";
import CreateAllUsersButton from "./UserManagement/CreateAllUsersButton";


class Dashboard extends Component {
    constructor() {
        super();
        this.state = {
            showUser: false,
            showAdmin: false
        };
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        if (!this.props.security.validToken) {
            this.props.history.push("/login");
        }
        // Depending on the user, different buttons will be available
        if(this.props.security.user.userType == "Admin") {
            this.setState({showAdmin: true})
        } else {
            this.setState({showUser: true})
        }
    }
    onSubmit(e) {
        e.preventDefault();
        this.props.logout();
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    render() {
        return (
            <div className="Dashboard">
            <div className="container">
                <div className="row">
                    <div className="col-md-12">
                        <h1 className="display-4 text-left">Dashboard</h1>
                        <br />
                        {/* Admin buttons - others beside requests to be implemented */}
                        <div>
                            {
                                this.state.showAdmin &&
                                <>
                                    <CreateAllUsersButton/>
                                    <CreateAllTransactionsButton/>
                                    <CreateAllBooksButton/>
                                    <CreateRequestButton/>
                                </>
                            }
                            {/* User buttons - to be implemented */}
                            {
                                this.state.showUser &&
                                <>
                                    <CreateUserOrderButton/>
                                    {/*<button className={"btn btn-lg btn-info"}> Orders </button>*/}
                                    <Link className="btn btn-lg btn-info " to="/newAd">
                                        Post an ad
                                    </Link>
                                </>
                            }
                                <span style={{float:"right"}}>
                                    <form onSubmit={this.onSubmit}><button className="btn btn-lg btn-info"> Log out </button></form>
                                </span>
                        </div>
                        <hr />
                        {/* User profile - to be seperated for cohesion */}
                        <p> Name: {this.props.security.user.fullName}
                            <span style={{float:"right"}}>
                                <button className="btn btn-info"> Edit </button>
                            </span>
                        </p>
                        <p> Role: {this.props.security.user.userType}</p>
                        <p> Address: {this.props.security.user.address}</p>
                        <p> Phone Number: {this.props.security.user.phoneNumber}</p>
                        <hr />
                    </div>
                </div>
            </div>
        </div>

        )
    }
}

Dashboard.propTypes = {
    logout: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    security: PropTypes.object.isRequired
};

const mapStateToProps = (state) => ({
    security: state.security,
    errors: state.errors,
    selectedAds: state.ad
});

export default connect(
    mapStateToProps, {logout}
)(Dashboard);

