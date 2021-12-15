import React, { Component } from "react";
import "../abu.css";

export default class AboutUs extends Component {
    render() {

        return (
            <div className="about">
                
<div className="about-section">
  <h1>About Us Page</h1>
  <p> Bookero company requires a system to provide online book selling and sharing
      services for their users. Our vision is to excel at delivering an intuitive eCommerce platform where a wide range
      of audiences can consume the best goods, offer recommendations and be part of the growing readers’ community.
      The website have two sets of functionality for managing users:
      Admin users and customer(buyer/seller or publisher) users, with different authorisation
      settings. Each type of user can see their own dashboard and functions
<p> </p>
      With a focus on flexibility and smooth experience, we want our product to reach millions of customers, 
      be it teenagers who want to read for fun, shop owners who want to sell their merchandise, or publication 
      organizations that just want to gain knowledge about the market. Our product can be considered valuable because
      we provide a fast, electrifying user interface that is customer-friendly, and a convenient way to link up with 
      the community, all the while having an immense reach within our consumer groups, which traditional retail
      often fails to achieve.

      <p></p>
      <p></p>
      <h2>Team Members Details</h2>
      Linda Vu - s3842580@student.rmit.edu.au (Developer)
      <p></p>
      Harshita Kumar - s3845841@student.rmit.edu.au (Scrum Master, Developer)
      <p></p>
      Dhruv Bachani - s3808392@student.rmit.edu.au (Developer)
      <p></p>
      Aditya Vadgama - s3845898@student.rmit.edu.au (Developer)
      <p></p>
      David Nguyen -   s3845788@student.rmit.edu.au (Developer)
   </p>
  
</div>

<h2 style= {{textAlign: 'center'}}>Our Values</h2>
<div className="row">
  <div className="column">
    <div className="card">
      <div className="container">
        <h4>Diversity</h4>
        <p>Our Team belives unity in diversity</p>
        <p></p>
      </div>
    </div>
  </div>

  <div className="column">
    <div className="card">
      <div className="container">
        <h4>Unity</h4>
        <p>Working in Unity to achieve the goals </p>
        <p></p>
      </div>
    </div>
  </div>
  
  <div className="column">
    <div className="card">
      <div className="container">
        <h4>Innovation</h4>
        <p>Encouraging for new ideas and recommendation </p>
        <p></p>
      </div>
    </div>
  </div>
</div>
            </div>
        )}
}