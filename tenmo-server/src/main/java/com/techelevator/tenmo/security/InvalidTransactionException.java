package com.techelevator.tenmo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid transaction, check IDs.")
public class InvalidTransactionException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidTransactionException(){
        super("Invalid transaction, check IDs.");
    }

}

