package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransactionController {

    private TransactionDao dao;

    public TransactionController(TransactionDao transactionDao) {
        dao = transactionDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public boolean sendFunds(@RequestBody Transaction transaction) {
        return dao.sendFunds(transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount());
    }

}
