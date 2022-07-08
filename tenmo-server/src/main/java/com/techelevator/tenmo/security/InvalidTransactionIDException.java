package com.techelevator.tenmo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid transaction, check transaction ID.")
public class InvalidTransactionIDException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidTransactionIDException(){
        super("Invalid transaction, check transaction ID.");
    }

}
