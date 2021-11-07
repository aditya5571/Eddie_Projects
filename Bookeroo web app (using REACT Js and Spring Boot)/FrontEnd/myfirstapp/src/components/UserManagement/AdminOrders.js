import React, { Component } from "react";
import {connect} from "react-redux";
import {getAllTransactions, getAllPurchasedBooks} from "../../actions/orderActions";
import { CSVLink } from "react-csv";

const headers = [
    { label: "User ID", key: "userId" },
    { label: "Date Ordered", key: "create_At" },
    { label: "Order ID", key: "id" },
    { label: "PayPal Order ID", key: "payPalOrderId" },
    { label: "PayPal Order Email", key: "payPalEmail" },
    // { label: "Title", key: "purchases" },
    { label: "Condition", key: "condition" },
    { label: "ISBN", key: "isbn" },
    { label: "Quantity", key: "quantity" },
    { label: "Seller", key: "seller" },
    { label: "Price", key: "cost" },
    { label: "Delivery Status", key: "deliveryStatus" },
    { label: "Shipping Address", key: "address" },
    { label: "Total Cost", key: "totalCost" }
];

class AdminOrders extends Component {

    constructor() {
        super();
        this.state = {
            csvData: [],
        };

        this.csvLinkEl = React.createRef();

        this.onChange = this.onChange.bind(this);
        this.downloadTransactionReport = this.downloadTransactionReport.bind(this);

    }

    componentDidMount() {
        this.props.getAllTransactions()
    }

    // Change value of the id based on which button was pressed
    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });
    }

    downloadTransactionReport = async () => {
        this.csvLinkEl.current.link.click();
    }

    render() {
        console.log(this.state.csvData)
        return (
            <div className="requests">
                <div className="container">
                    <div className="col-md-12 text-center">
                        <h1 className="display-4 mb-4">All orders</h1>
                        <input type="button" className="btn btn-lg btn-info" value="Download CSV All User Transactions" onClick={this.downloadTransactionReport} />
                        <CSVLink
                            headers={headers}
                            filename="all_user_transactions.csv"
                            data={this.props.allTransactions.map(item => ({
                                userId: item.userId,
                                create_At: item.create_At,
                                id: item.id,
                                payPalOrderId: item.payPalOrderId,
                                payPalEmail: item.payPalEmail,
                                condition: item.purchases.map(book => book.condition),
                                isbn: item.purchases.map(book => book.isbn),
                                quantity: item.purchases.map(book => book.quantity),
                                seller: item.purchases.map(book => book.seller),
                                cost: item.purchases.map(book => book.cost),
                                deliveryStatus: item.purchases.map(book => book.deliveryStatus),
                                address: item.address,
                                totalCost: item.totalCost
                            }))}
                            ref={this.csvLinkEl}
                        />
                    </div>
                    <br />
                    <hr />
                    <div className="col-md-12 align-left">
                    </div>
                </div>
                <div className="container">
                    <div className="row">
                        {this.props.allTransactions.map((transaction) => {
                            return (
                                <div key={transaction.id} className="col">
                                    <div className="card">
                                        <div className="card-body">
                                            <h5 className="card-title">UserID: {transaction.userId} </h5>
                                            <h5 className="card-title">OrderID: {transaction.id} </h5>
                                            <h5 className="card-title">Paypal OrderID: {transaction.payPalOrderId} </h5>
                                            <h6 className="card-subtitle mb-2 text-muted"> Date Ordered: {transaction.create_At} </h6>
                                            <br />
                                            <h5 className="card-text"> Books </h5>
                                            <hr/>
                                            {transaction.purchases.map((book) => {
                                                return (
                                                    <div key={book.id} className="row">
                                                        {/* TODO: get book link */}
                                                        <p className="card-text" className="col"> Title: Get book
                                                            object </p>
                                                        <p className="card-text" className="col"> ISBN: {book.isbn} </p>
                                                        <p className="card-text"
                                                           className="col"> Condition: {book.condition} </p>
                                                        <p className="card-text"
                                                           className="col"> Quantity: {book.quantity} </p>
                                                        {/* TODO: get seller name */}
                                                        <p className="card-text"
                                                           className="col"> Seller: {book.seller} </p>
                                                        <p className="card-text" className="col"> Delivery
                                                            Status: {book.deliveryStatus} </p>
                                                        <p className="card-text" className="col"> Price:
                                                            ${book.cost} </p>
                                                        <hr/>
                                                    </div>
                                            );})}
                                            <p className="card-text">  Address: {transaction.address} </p>
                                            <p className="card-text">  Total Cost: {transaction.totalCost} </p>
                                            <p className="card-text"> Refund Button </p>
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
        allTransactions: state.order.allTransactions,
        security: state.security
    }
};

export default connect(
    mapStateToProps,
    { getAllTransactions }
)(AdminOrders);


