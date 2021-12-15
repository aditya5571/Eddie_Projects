package com.rmit.sept.bk_bookcatalogservices.exceptions;

public class InvalidIsbnResponse {

    private String field;

    public InvalidIsbnResponse(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}