import React, {Component} from "react";
import axios from "axios";
import Table from "../Layout/Table"
import {connect} from "react-redux";
import {login} from "../../actions/securityActions";
import {Button} from "react-bootstrap";
import {getShoppingCart} from "../../actions/orderActions";
import {Styles} from "../Layout/TableStyles";


class Sellers extends Component {
    constructor(props) {
        super(props);
        this.state = {
            sellers: [],
            condition: "new",
            isbn: this.props.match.params.isbn,
            selectedAds: []
        }
        this.onChange = this.onChange.bind(this);
        this.addToCart = this.addToCart.bind(this);
    }


    componentDidMount() {

            axios.get("http://localhost:8081/api/books/" + this.state.isbn + "/allAds?condition=" + this.state.condition).then((res) => {
                this.setState({sellers: res.data})
            })


    }

    async onChange(e) {
        await axios.get("http://localhost:8081/api/books/" + this.state.isbn + "/allAds?condition=" + e.target.value).then((res) => {
            this.setState({sellers: res.data})
        })

    }

    addToCart(e) {
    if(this.props.security.validToken)
    {
        const res = axios.post(`http://localhost:8084/api/orders/addToCart`, this.state.selectedAds).then(() => {
            this.props.history.push("/cart")
        });
    }
    else{
        alert("Please Login to continue.")
    }
    }

    columns = [{
        Header: 'Available Options',
        columns:
            [
                {
                    Header: 'Seller-id',
                    accessor: 'userId'
                },

                {
                    Header: 'Price',
                    accessor: 'price'
                }

            ]
    }]


    render() {
        return (
            <div >
                <select className="form-control" onChange={this.onChange}>
                    <option value="new">New</option>
                    <option value="old">Old</option>
                </select>
                <Styles>
                <Table columns={this.columns} data={this.state.sellers}
                       onRowSelect={rows => this.state.selectedAds = rows}/>
                </Styles>
                <div style={{display:"flex", justifyContent:"flex-end"}}>
                <Button style={{marginRight:"60px"}} onClick={this.addToCart}>Add to cart</Button>
                </div>
            </div>
        )
    }


}


const mapStateToProps = (state) => {
    return {
        security: state.security
    }
};

export default connect(
    mapStateToProps,
    {}
)(Sellers);


