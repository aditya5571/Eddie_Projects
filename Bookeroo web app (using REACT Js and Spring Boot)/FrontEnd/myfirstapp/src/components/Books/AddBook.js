import React, { Component } from "react";
import { connect } from "react-redux";
import {createBook, getAllCategories} from "../../actions/bookActions";

class AddBook extends Component {
  constructor() {
    super();

    this.state = {
      name: "",
      author: "",
      category: "",
      description: "",
      isbn: "",
      image: "",
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(e) {
    this.setState({ [e.target.name]: e.target.value });
  }



  onSubmit(e) {
    e.preventDefault();
    const newBook = {
      name: this.state.name,
      author: this.state.author,
      category: this.state.category,
      description: this.state.description,
      isbn: this.state.isbn,
      image: this.state.image
    };



    this.props.createBook(newBook, this.props.history);
    alert("New Book Successfully Created.")
  }

  async componentDidMount() {
    this.props.getAllCategories().then(()=>{
      this.setState({"category": this.props.categories[0]})
    })

  }

  render() {
    return (
      <div className="Person">
        <div className="container">
          <div className="row">
            <div className="col-md-8 m-auto">
              <h5 className="display-4 text-center">Create New Book</h5>
              <hr />
              <form onSubmit={this.onSubmit}>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg "
                    placeholder="Name"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChange}
                    required={true}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Author"
                    name="author"
                    value={this.state.author}
                    onChange={this.onChange}
                    required={true}
                  />
                </div>
                <div className="form-group">
                  <select className="form-control" onChange={this.onChange} name="category" required={true}>
                    {
                      this.props.categories.map((value)=>{

                        return <option value={value} key={value} >{value}</option>
                      })
                    }
                  </select>
                </div>
                <div className="form-group">
                  <textarea
                    className="form-control form-control-lg"
                    placeholder="Description"
                    name="description"
                    value={this.state.description}
                    onChange={this.onChange}
                    required={true}
                  />
                </div>
                <div className="form-group">
                  <input
                    className="form-control form-control-lg"
                    placeholder="ISBN Number"
                    name="isbn"
                    type="number"
                    value={this.state.isbn}
                    onChange={this.onChange}
                    required={true}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="text"
                    className="form-control form-control-lg"
                    placeholder="Image URL"
                    name="image"
                    value={this.state.image}
                    onChange={this.onChange}
                  />
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
    );
  }
}


const mapStateToProps = (state) => ({
  errors: state.errors,
  categories: state.book.allCategories
});

export default connect(mapStateToProps, { createBook, getAllCategories })(AddBook);
