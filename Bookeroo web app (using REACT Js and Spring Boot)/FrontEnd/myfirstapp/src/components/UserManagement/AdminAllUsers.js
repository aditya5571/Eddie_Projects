import React, { Component } from "react";
import {connect} from "react-redux";
import {getAllUsers, banUser} from "../../actions/userActions";

class AdminAllUsers extends Component {

    constructor() {
        super();
        this.state = {
            id: "",
        };
        this.onChange = this.onChange.bind(this);
        this.onBan = this.onBan.bind(this);
    }

    componentDidMount() {
        this.props.getAllUsers()
    }
    onBan(e) {
        e.preventDefault();
        this.props.banUser(this.state)
    }
    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });

    }

    render() {
        console.log(this.props)
        return (
            <div className="requests">
                <div className="container">
                    <div className="col-md-12 text-center">
                        <h1 className="display-4 mb-4">All Users</h1>
                    </div>
                    <br />
                    <hr />
                    <div className="col-md-12 align-left">
                    </div>
                </div>
                <div className="container">
                <div className="row row-cols-1 row-cols-sm-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4">
                    {this.props.user.allUsers.map((req) => {
                        return (
                            <div key={req.id} className="col mb-4">
                                <div className="card">
                                    <div className="card-body">
                                        {/* Publisher/Shop Owner Information */}
                                        <h5 className="card-title">Full name: {req.fullName}</h5>
                                        <h6 className="card-subtitle mb-2 text-muted"> Phone Number: {req.phoneNumber} </h6>
                                        <p className="card-text"> Role: {req.userType} </p>
                                        <p className="card-text">  Email: {req.username} </p>
                                        {/* Ban button */}
                                        <br/>
                                        <form  style={{textAlign: "center"}} onSubmit={this.onBan}>
                                            <button type="submit"
                                                    className={"btn btn-danger"}
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
    { getAllUsers, banUser }
)(AdminAllUsers);


