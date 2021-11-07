package com.rmit.sept.bk_bookcatalogservices.exceptions;

public class InvalidConditionResponse {

    private String field;

    public InvalidConditionResponse(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}