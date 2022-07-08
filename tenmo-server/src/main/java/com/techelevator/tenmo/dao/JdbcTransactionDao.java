package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private AccountDao dao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(DataSource dataSource, AccountDao accountDao){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        dao = accountDao;
    }

    @Override
    public boolean sendFunds(long fromAccountId, long toAccountId, BigDecimal sendAmount){
        String sql = "UPDATE account SET balance = balance - ? WHERE account_id = ?;";
        String sql2 = "UPDATE account SET balance = balance + ? WHERE account_id = ?;";
        String sql3 = "INSERT INTO transaction (from_acct, to_acct, amount, date_time, status) VALUES (?, ?, ?, ?, ?)";
        if (sendAmount.compareTo(dao.getBalance(fromAccountId)) > 0){
            return false;
        } else if (sendAmount.compareTo(BigDecimal.ZERO) <= 0){
            return false;
        }
        jdbcTemplate.update(sql, sendAmount, fromAccountId);
        jdbcTemplate.update(sql2, sendAmount, toAccountId);
        jdbcTemplate.update(sql3, fromAccountId, toAccountId, sendAmount, LocalDateTime.now(), "APPROVED");

        return true;
    }

    public List<Transaction> findTransactionByUserId(Long userId){
        String sql = "SELECT transaction_id, from_acct, to_acct, amount, date_time, status" +
                " FROM transaction AS t JOIN account as a ON a.account_id = t.from_acct OR a.account_id = t.to_acct" +
                " JOIN tenmo_user AS tu ON a.user_id = tu.user_id " +
                " WHERE tu.user_id = ?;";
        List<Transaction> transactions = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    public Transaction findByTransactionId(int transactionId){
        String sql = "SELECT transaction_id, from_acct, to_acct, amount, date_time, status FROM transaction WHERE transaction_id = ?;";
        Transaction transaction =  new Transaction();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transactionId);
        if (result.next()){
            transaction = mapRowToTransaction(result);
        }
        return transaction;
    }

    private Transaction mapRowToTransaction(SqlRowSet rs) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setSenderId(rs.getInt("from_acct"));
        transaction.setReceiverId(rs.getInt("to_acct"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setStatus(rs.getString("status"));
        return transaction;
    }

}
