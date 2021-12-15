package com.rmit.sept.bk_bookcatalogservices.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidIsbnException extends RuntimeException {

    public InvalidIsbnException(String message) {
        super(message);
    }
}
