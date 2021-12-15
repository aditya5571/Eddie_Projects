import React, { Component } from 'react'
import {Link} from "react-router-dom";




export default class ContactUs extends Component {
    constructor() {
        super();
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e) {
        e.preventDefault();
        window.location.reload(false);
    }
    render() {
        return (
            <div>
                <div className="w3-container w3-content w3-padding-64 contactUS" style={{maxWidth: '800px'}} id="contact">
    <h2 className="w3-wide w3-center">CONTACT US</h2>
    <p className="w3-opacity w3-center"><i>Get it in touch</i></p>
    <div className="w3-row w3-padding-32">
      <div className="w3-col m6 w3-large w3-margin-bottom">
        <i className="fa fa-map-marker" style={{width:'30px'}}></i> CBD, Melbourne<br></br>
        <i className="fa fa-phone" style={{width:'30px'}}></i> Phone: +61468927199<br></br>
        <i className="fa fa-envelope" style={{width:'30px'}}> </i> Email: info@bookero.com.au<br></br>
        
      </div>
      <div className="w3-col m6">
        <form>
          <div className="w3-row-padding" style={{margin:'0 -16px 8px -16px'}}>
            <div className="w3-half">
              <input className="w3-input w3-border" type="text" placeholder="Name" required name="Name"/>
            </div>
            <div className="w3-half">
              <input className="w3-input w3-border" type="text" placeholder="Email" required name="Email"/>
            </div>
          </div>
          <input className="w3-input w3-border" type="text" placeholder="Message" required name="Message"/>
          <button className="w3-button w3-black w3-section w3-right" type="submit">SEND</button>


        </form>
      </div>
    </div>
    <div className = "w3-container w3-padding-64 w3-center w3-xlarge">
        <a href = "https://www.facebook.com/"><i className="fa fa-facebook-official w3-hover-opacity"></i></a>

        <a href = "https://www.instagram.com/"><i className="fa fa-instagram w3-hover-opacity"></i></a>
        <a href = "https://www.twitter.com/"><i className="fa fa-twitter w3-hover-opacity"></i></a>
        <a href = "https://www.linkedin.com/"><i className="fa fa-linkedin w3-hover-opacity"></i></a>
    </div>
  </div>
</div>
            
        )
    }
}