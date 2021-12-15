import React, { Component } from "react";

class Footer extends Component {
  render() {
    return (
      <div className="footer">
        <nav className="navbar navbar-expand-sm navbar-dark bookeroo-nav footer">
          <div className="bookeroo-footer">
            <div className="collapse navbar-collapse" id="mobile-nav">
              <ul className="navbar-nav mr-auto">
                <li className="nav-item">
                  <a className="nav-link" href="/aboutUs">
                    About Us
                  </a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href="/contactUs">
                    Contact Us
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
      </div>
    );
  }
}
export default Footer;
