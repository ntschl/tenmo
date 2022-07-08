package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.security.InvalidTransactionException;
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
    @RequestMapping(path = "/send/{toUsername}", method = RequestMethod.POST)
    public boolean sendFunds(@Valid @RequestBody Transaction transaction, @PathVariable String toUsername, Principal principal) throws InvalidTransactionException {
        if(transaction.getSenderId() == transaction.getReceiverId()){
            throw new InvalidTransactionException();
        }
        return dao.sendFunds(toUsername, principal.getName(), transaction.getAmount());
    }

    @RequestMapping(path = "/transactions/user/{userId}", method = RequestMethod.GET)
    public List<Transaction> findTransactionByUserId(@PathVariable Long userId){
        return dao.findTransactionByUserId(userId);
    }

    @RequestMapping(path = "/transactions/{transactionId}", method = RequestMethod.GET)
    public Transaction findByTransactionId(@PathVariable int transactionId){
        return dao.findByTransactionId(transactionId);
    }

}
