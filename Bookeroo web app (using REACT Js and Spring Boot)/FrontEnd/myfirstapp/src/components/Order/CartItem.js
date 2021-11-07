import React, {useEffect, useState} from 'react'
import {Button} from "react-bootstrap";
import axios from "axios";
import {connect} from "react-redux";
import {getShoppingCart} from "../../actions/orderActions";
import {getBook} from "../../actions/bookActions";
import {Link} from "react-router-dom";



function CartItem ({item, onRemove}){

    const[book, setBook] = useState()

    function deleteItem(id){
        axios.delete("http://localhost:8084/api/orders/deleteCartItem/"+id)
        onRemove()
    }

    useEffect(()=>{
        axios.get(`http://localhost:8081/api/books/`+item.isbn).then((res)=>(setBook(res.data)));
    })
    return(
        <div className="card" style={{textDecoration:'none', flexDirection: "row",alignItems: "center", justifyContent:"space-around"}}>

                <div>
                    {book && <Link to={`/bookPage/${book.isbn}`} >
                    <img
                        className="img-class"
                        src={book.image}
                        alt="Image not found"
                        onError={(e) => {
                            e.target.onerror = null;
                            e.target.src = "/default.jpg";
                        }}
                    />
                    </Link>}

                </div>
                <div>
                    <div>Ad-id: {item.id}</div>

                    {book && <div>Name: {book.name}</div>}

                    <div>Seller Id: {item.userId}</div>

                    <div>Condition: {item.condition}</div>

                    <div>Price: {item.price}</div>
                </div>
            <Button  onClick={()=>deleteItem(item.id)}>Remove</Button>
        </div>
    )
}

export default CartItem;
