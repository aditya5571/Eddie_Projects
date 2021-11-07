import React, { Component } from "react";

class Profile extends Component {
  render() {
    return (
      <div className="profile">
        <div className="light-overlay landing-inner text-dark">
          <div className="container">
            <div className="row">
              <div className="col-md-12">
                <h1 className="display-3 mb-4">
                  Profile Dashboard
                </h1>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Profile;