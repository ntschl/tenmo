package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransactionDao {

    boolean sendFunds(long fromAccountId, long toAccountId, BigDecimal sendAmount);
}
