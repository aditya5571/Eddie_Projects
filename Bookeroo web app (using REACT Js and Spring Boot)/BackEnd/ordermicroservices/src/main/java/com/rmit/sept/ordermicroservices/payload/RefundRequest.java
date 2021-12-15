package com.rmit.sept.ordermicroservices.payload;

import javax.validation.constraints.NotBlank;

public class RefundRequest {

    @NotBlank(message = "ID cannot be blank")
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
