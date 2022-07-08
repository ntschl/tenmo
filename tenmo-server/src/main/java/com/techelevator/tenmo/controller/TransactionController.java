package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransactionController {

    private TransactionDao dao;

    public TransactionController(TransactionDao transactionDao) {
        dao = transactionDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public boolean sendFunds(@Valid @RequestBody Transaction transaction, Principal principal) {
        if(transaction.getSenderId() == transaction.getReceiverId()){
            return false;
        }
        return dao.sendFunds(transaction.getSenderId(), transaction.getReceiverId(), transaction.getAmount());
    }

    @RequestMapping(path = "/transactions/{userId}", method = RequestMethod.GET)
    public List<Transaction> findTransactionByUserId(@PathVariable Long userId){
        return dao.findTransactionByUserId(userId);
    }

}
