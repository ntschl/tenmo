package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class JdbcTransactionDao implements TransactionDao {

    private AccountDao dao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransactionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public boolean sendFunds(long fromAccountId, long toAccountId, BigDecimal sendAmount){
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        String sql2 = "UPDATE account SET balance = ? WHERE account_id = ?;";
        String sql3 = "INSERT INTO transaction (from_acct, to_acct, amount, date_time) VALUES (?, ?, ?, ?)";

        jdbcTemplate.queryForRowSet(sql, (sendAmount.add(dao.getBalance(toAccountId))), toAccountId);
        jdbcTemplate.queryForRowSet(sql2, (dao.getBalance(fromAccountId).subtract(sendAmount)), fromAccountId);
        jdbcTemplate.queryForRowSet(sql3, fromAccountId, toAccountId, sendAmount, LocalDate.now());

        return true;
    }

}
