import React, { Component } from "react";
import {connect} from "react-redux";
import {getAllUnapprovedUsers, approveUser, banUser} from "../../actions/userActions";

class Requests extends Component {

    constructor() {
        super();
        this.state = {
            id: "",
        };
        this.onChange = this.onChange.bind(this);
        this.onApprove = this.onApprove.bind(this);
        this.onBan = this.onBan.bind(this);

    }

    // Retrieves all the unapprovedUsers
    componentDidMount() {
        this.props.getAllUnapprovedUsers()
    }

    // Functions that would call the userAction methods
    onApprove(e) {
        e.preventDefault();
        console.log(this.state)
        this.props.approveUser(this.state)
    }
    onBan(e) {
        e.preventDefault();
        this.props.banUser(this.state)
    }

    // Change value of the id based on which button was pressed
    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });

    }

    render() {
        return (
            <div className="requests">
                <div className="container">
                    <div className="col-md-12 text-center">
                        <h1 className="display-4 mb-4">Publishers and Book owners to be approved</h1>
                    </div>
                    <br />
                    <hr />
                    <div className="col-md-12 align-left">
                    </div>
                </div>
                <div className="container">
                <div className="row row-cols-1 row-cols-sm-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4">
                    {this.props.user.userRequests.map((req) => {
                        return (
                            <div key={req.id} className="col mb-4">
                                <div className="card">
                                    <div className="card-body">
                                        {/* Publisher/Shop Owner Information */}
                                        <h5 className="card-title">Full name: {req.fullName}</h5>
                                        <h6 className="card-subtitle mb-2 text-muted"> Phone Number: {req.phoneNumber} </h6>
                                        <br />
                                        <p className="card-text"> Role: {req.userType} </p>
                                        <p className="card-text"> Shop name: {req.shopName} </p>
                                        <p className="card-text"> ABN number: {req.abnNumber} </p>
                                        <p className="card-text">  Address: {req.address} </p>
                                        <p className="card-text">  Email: {req.username} </p>
                                        {/* Approve and Ban button */}
                                        <form onSubmit={this.onApprove}>
                                            <button type="submit"
                                                    name="id"
                                                    value={req.id}
                                                    onClick={this.onChange}
                                            >
                                                Approve
                                            </button>
                                        </form>
                                        <br/>
                                        <form onSubmit={this.onBan}>
                                            <button type="submit"
                                                    name="id"
                                                    value={req.id}
                                                    onClick={this.onChange}
                                            >
                                                Ban
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        );
                    })}
                </div>
                </div>

            </div>
        )

    }
}

const mapStateToProps = (state) => {
    return{
        user: state.user
    }
};

export default connect(
    mapStateToProps,
    { getAllUnapprovedUsers, approveUser, banUser }
)(Requests);


