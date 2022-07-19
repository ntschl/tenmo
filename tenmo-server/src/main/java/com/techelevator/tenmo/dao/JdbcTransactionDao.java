package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import com.techelevator.tenmo.security.InvalidAmountException;
import com.techelevator.tenmo.security.InvalidUserIDException;
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
    public boolean sendFunds(String toUser, String fromUser, BigDecimal sendAmount){
        long fromId = dao.getAccountsByUsername(fromUser).get(0).getAccountId();
        long toId;
        try {
            toId = dao.getAccountsByUsername(toUser).get(0).getAccountId();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidUserIDException();
        }
        String sql = "UPDATE account SET balance = balance - ? " +
                    " WHERE account_id = ?";
        String sql2 = "UPDATE account SET balance = balance + ? " +
                    " WHERE account_id = ?";
        String sql3 = "INSERT INTO transaction (from_acct, to_acct, amount, date_time, status) VALUES (?, ?, ?, ?, ?)";
        if (sendAmount.compareTo(dao.getBalance(fromId)) > 0){
            throw new InvalidAmountException();
        } else if (sendAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException();
        }
        jdbcTemplate.update(sql, sendAmount, fromId);
        jdbcTemplate.update(sql2, sendAmount, toId);
        jdbcTemplate.update(sql3, fromId, toId, sendAmount, LocalDateTime.now(), "APPROVED");

        return true;
    }

    public List<Transaction> findTransactionsByUsername(String username) {
        String sql = "select transaction_id, from_acct, to_acct, amount, date_time, status from transaction as t " +
                "join account as a on a.account_id = t.from_acct OR a.account_id = t.to_acct " +
                "join tenmo_user as tu on a.user_id = tu.user_id " +
                "where username = ?;";
        List<Transaction> transactions = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()) {
            Transaction transaction = mapRowToTransaction(results);
            transactions.add(transaction);
        }
        return transactions;
    }

    public Transaction findByTransactionId(int transactionId, String username){
        String sql = "select transaction_id, from_acct, to_acct, amount, date_time, status from transaction as t " +
                "join account as a on a.account_id = t.from_acct OR a.account_id = t.to_acct " +
                "join tenmo_user as tu on a.user_id = tu.user_id " +
                "where username = ? " +
                "AND transaction_id = ?;";
        Transaction transaction =  new Transaction();
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username, transactionId);
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
