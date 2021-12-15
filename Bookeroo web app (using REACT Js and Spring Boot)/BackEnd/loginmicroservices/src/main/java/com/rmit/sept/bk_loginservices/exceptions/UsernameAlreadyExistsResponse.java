package com.rmit.sept.bk_loginservices.exceptions;

public class UsernameAlreadyExistsResponse {

    private String field;

    public UsernameAlreadyExistsResponse(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}