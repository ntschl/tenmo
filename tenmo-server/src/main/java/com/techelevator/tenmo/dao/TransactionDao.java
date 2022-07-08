package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    public boolean sendFunds(String toUser, String fromUser, BigDecimal sendAmount);

    public List<Transaction> findTransactionByUserId(Long userId);

    public Transaction findByTransactionId(int transactionId);

}
