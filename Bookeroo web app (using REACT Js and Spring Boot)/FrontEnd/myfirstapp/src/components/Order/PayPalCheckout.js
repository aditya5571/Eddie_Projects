import axios from "axios";
import React, {Component, useEffect, useState} from "react";
import ReactDOM from "react-dom";

const PayPalButton = window.paypal.Buttons.driver("react", { React, ReactDOM });

function PayPalCheckout(props) {
        function _createOrder(data, actions) {
            let arr = [];
            let sameSeller = [];
            props.checkout.cartItems.map(item => {
                if((sameSeller.indexOf(item.userId) > -1) == false) {
                    let items = [];
                    let cost = 0;
                    props.checkout.cartItems.map(item2 => {
                        if(item2.userId == item.userId) {
                            items.push({
                                name: "Book",
                                description: item2.condition,
                                sku: item2.isbn,
                                unit_amount: {
                                    currency_code: "AUD",
                                    value: item2.price
                                },
                                quantity: "1"
                            });
                            cost = cost + item2.price;
                        };
                    });
                    arr.push({
                        reference_id : item.userId,
                        amount: {
                            currency_code: "AUD",
                            value: cost,
                            breakdown: {
                                item_total: {
                                    currency_code: "AUD",
                                    value: cost
                                }
                            }
                        },
                        items: items
                    })
                    sameSeller.push(item.userId);
                }
            });
        return actions.order.create({
            intent: 'CAPTURE',
            payer: {
                name: {
                    given_name: props.checkout.security.user.fullName.split(" ")[0],
                    surname: props.checkout.security.user.fullName.split(" ")[1]

                },
                address: {
                    address_line_1: props.address.address_line_1, // etc "[[${user.addressLine1}]]"
                    address_line_2: props.address.address_line_2,
                    admin_area_2: props.address.admin_area_2,
                    admin_area_1: props.address.admin_area_1,
                    postal_code: props.address.postal_code,
                    country_code: props.address.country_code
                },
                email_address:  props.checkout.security.user.username,
                phone: {
                    phone_type: "MOBILE",
                    phone_number: {
                        national_number:  props.checkout.security.user.phoneNumber
                    }

                }
            },
            purchase_units: arr
        });
    }
    async function _onApprove(data, actions) {
        let order = await actions.order.capture();
        let orderId = order.id;
        const CheckOutRequest = {
            paypalOrderId: order.id,
            userId: props.checkout.security.user.id
        };
        await axios.post("http://localhost:8084/api/orders/checkout", CheckOutRequest);
        props.checkout.history.push('/bookCatalog');
        alert("Thanks for purchasing! OrderID: " + orderId + " Status: Processing");
        return order;
    }
    function _onError(message, err) {
        console.log(err);
        alert(message);
    }

    return (
        <div className="App">
            <PayPalButton
                createOrder={(data, actions) => _createOrder(data, actions)}
                onApprove={(data, actions) => _onApprove(data, actions)}
                onCancel={() => _onError("Payment has been cancelled")}
                onError={(err) => _onError("Something wrong with the order details!", err)}
            />
        </div>
    );
}

export default (PayPalCheckout);
