package com.techelevator.tenmo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid transaction amount.")
public class InvalidAmountException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public InvalidAmountException(){
        super("Invalid transaction amount.");
    }

}
