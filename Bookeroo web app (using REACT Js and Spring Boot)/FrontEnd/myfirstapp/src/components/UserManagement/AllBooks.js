import React, { Component } from "react";
import {connect} from "react-redux";
import BookCard from "../BookManagement/BookCard";
import {getAllBooks} from "../../actions/bookActions";
import { CSVLink } from "react-csv";

const headers = [
    { label: "Book ID", key: "id" },
    { label: "Title", key: "name" },
    { label: "Author", key: "author" },
    { label: "Category", key: "category" },
    { label: "Description", key: "description" },
    { label: "ISBN", key: "isbn" },
    { label: "Book Cover", key: "image" }
];

class AllBooks extends Component {

    constructor() {
        super();

        this.csvLinkEl = React.createRef();

        this.onChange = this.onChange.bind(this);
        this.downloadBookReport = this.downloadBookReport.bind(this);

    }

    componentDidMount() {
        this.props.getAllBooks()
    }


    onChange(e) {
        this.setState({ [e.target.name]: e.target.value });

    }

    downloadBookReport = async () => {
        console.log(this.props.books);
        this.csvLinkEl.current.link.click();
    }

    render() {
        return (
            <div className="requests">
                <div className="container">
                    <div className="col-md-12 text-center">
                        <h1 className="display-4 mb-4">All books</h1>
                        <input type="button" className="btn btn-lg btn-info" value="Download CSV All Books" onClick={this.downloadBookReport} />
                        <CSVLink
                            headers={headers}
                            filename="all_books.csv"
                            data={this.props.books}
                            ref={this.csvLinkEl}
                        />
                    </div>
                    <br />
                    <hr />
                    <div className="col-md-12 align-left">
                    </div>
                </div>
                <div className="container">
                    <div className="row row-cols-1 row-cols-sm-1 row-cols-md-2 row-cols-lg-3 row-cols-xl-4">
                        {this.props.books.map((value)=>{
                            return (
                                <div className="col mb-4">
                                    <div className="card">
                                        <div className="card-body">
                                            <BookCard book={value} key={value.isbn}/>
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
        books: state.book.books
    }
};

export default connect(
    mapStateToProps,
    {getAllBooks}
)(AllBooks);
