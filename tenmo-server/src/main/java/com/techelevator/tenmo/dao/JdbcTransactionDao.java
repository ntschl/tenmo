package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;

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
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        String sql2 = "UPDATE account SET balance = ? WHERE account_id = ?;";
        String sql3 = "INSERT INTO transaction (from_acct, to_acct, amount, date_time) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, (sendAmount.add(dao.getBalance(toAccountId))), toAccountId);
        jdbcTemplate.update(sql2, (dao.getBalance(fromAccountId).subtract(sendAmount)), fromAccountId);
        jdbcTemplate.update(sql3, fromAccountId, toAccountId, sendAmount, LocalDate.now());

        return true;
    }

    private Transaction mapRowToTransaction(SqlRowSet rs) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));
        transaction.setSenderId(rs.getInt("from_acct"));
        transaction.setReceiverId(rs.getInt("to_acct"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        return transaction;
    }

}
