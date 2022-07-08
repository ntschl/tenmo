package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransactionDTO {

    private String toUsername;
    private BigDecimal amount;

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
