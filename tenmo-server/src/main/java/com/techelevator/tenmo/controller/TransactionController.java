package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/send/{toAccountId}/{fromAccountId}/{sendAmount}", method = RequestMethod.PUT)
    public boolean sendFunds(@PathVariable long toAccountId, @PathVariable long fromAccountId, @PathVariable BigDecimal sendAmount) {
        return dao.sendFunds(toAccountId, fromAccountId, sendAmount);
    }

}
