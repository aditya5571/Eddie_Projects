import React, { Component } from "react";
import CreateBookButton from "./CreateBookButton";

class ManageBooks extends Component {
  render() {
    return (
      <div className="Persons">
        <div className="container">
          <div className="row">
            <div className="col-md-12">
              <br />
              <h2 className="display-5 text-center">
                BOOKEROO Book Catalog Management
              </h2>
              <br />
              <div className="display-5 text-center">
                <CreateBookButton /> <br/><br/>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default ManageBooks;
