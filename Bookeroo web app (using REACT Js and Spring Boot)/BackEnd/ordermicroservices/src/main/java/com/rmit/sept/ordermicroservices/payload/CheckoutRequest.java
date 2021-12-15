package com.rmit.sept.ordermicroservices.payload;

import javax.validation.constraints.NotBlank;

public class CheckoutRequest {
    @NotBlank(message = "OrderID cannot be blank")
    private String paypalOrderId;
    @NotBlank(message = "ID cannot be blank")
    private String userId;

    public String getPaypalOrderId() {
        return paypalOrderId;
    }

    public void setPaypalOrderId(String paypalOrderId) {
        this.paypalOrderId = paypalOrderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
