import React, {Component} from "react";
import {connect} from "react-redux";
import {getAllBooks, getAllCategories, postAd} from "../../actions/bookActions";
import axios from "axios";
import {GET_BOOKS} from "../../actions/types";


class PostAd extends Component{
    constructor() {
        super();
        this.state = {
            userId: "",
            isbn: "",
            condition: "old",
            price:"",
        }
        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

    }

    onSubmit(e) {
        e.preventDefault();
        const AdRequest = {
            isbn: this.state.isbn,
            condition: this.state.condition,
            price: this.state.price,
            userId: this.props.user.id
        };
        console.log(AdRequest)
        this.props.postAd(AdRequest, this.props.history);
    }

    onChange(e) {

        this.setState({ [e.target.name]: e.target.value });
    }


    async componentDidMount() {
        this.props.getAllBooks().then(()=>{
            if(this.props.books.length !== 0) {
                this.setState({
                    isbn: this.props.books[0].isbn
                })
            }
        });
        this.state.userId = this.props.user.id
    }

    render(){
        return(

            <div className="postAd">
                <div className="container">
                    <div className="row">
                        <div className="col-md-8 m-auto">
                            <h5 className="display-4 text-center">Post an Ad.</h5>
                            <hr />
                            <form onSubmit={this.onSubmit}>
                                <div className="form-group">
                                    <select className="form-control" onChange={this.onChange} name="isbn">
                                        {
                                            this.props.books.map((value)=>{
                                                return <option value={value.isbn} key={value.isbn}>{value.isbn}-{value.name}</option>
                                            })
                                        }
                                    </select>
                                </div>

                                <div className="form-check form-check-inline"   >
                                    <input className="form-check-input" type="radio" name="condition" id="new"
                                           value="new" disabled={(this.props.user.userType === "Customer")? true:false} onChange={this.onChange}/>
                                        <label className="form-check-label" htmlFor="new">New</label>
                                </div>
                                <div className="form-check form-check-inline">
                                    <input className="form-check-input" name="condition" type="radio" defaultChecked={true}
                                           value="old" onChange={this.onChange}/>
                                        <label className="form-check-label" htmlFor="old">Old</label>
                                </div>
                            <br />
                                <div className="form-inline">
                                    <label htmlFor="price" className="mr-sm-2">Price</label>
                                    <input type="number" min="1" step="any" className="form-control mr-sm-2 mb-2" id="price"
                                            onChange={this.onChange} name="price" value={this.state.price} placeholder="price"/>
                                </div>

                                <input
                                    type="submit"
                                    className="btn btn-primary btn-block mt-4"
                                />
                            </form>

                </div>
                </div>
                </div>
            </div>
        )

    }
}

const mapStateToProps = (state) => {
    return({
        books: state.book.books,
        user: state.security.user,
    })
};

export default connect(
    mapStateToProps,
    {getAllBooks, postAd}
)(PostAd);