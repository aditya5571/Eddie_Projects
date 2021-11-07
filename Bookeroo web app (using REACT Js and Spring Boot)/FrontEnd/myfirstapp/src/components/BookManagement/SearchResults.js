import React, { Component, useState } from "react";
import {connect} from "react-redux";
import BookCard from "./BookCard";

class SearchResults extends Component {
    constructor() {
        super();
        this.state = {
            books: []
        }
    }
    render() {

        return (
            <div>
                <h1>Search Results</h1>
                {(this.props.books.length!==0) && !this.props.books.includes(null) && <div className="search-grid">
                    {this.props.books.map((value) => {
                        return <div><BookCard book={value}/></div>
                    })}
                </div>}

                {((this.props.books.length===0)||this.props.books.includes(null)) && <div>
                    <h3>No Results found</h3>
                </div>}
            </div>
        );
    }
}

const mapStateToProps = (state) => {

    return{
        books: state.book.books
    }
};

export default connect(
    mapStateToProps
)(SearchResults);