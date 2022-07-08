package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionDao {

    boolean sendFunds(long fromAccountId, long toAccountId, BigDecimal sendAmount);

    public List<Transaction> findTransactionByUserId(Long userId);

    public Transaction findByTransactionId(int transactionId);

}
