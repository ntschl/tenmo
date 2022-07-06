package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao{

    public static List<Account> accounts = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcAccountDao(){
        this.jdbcTemplate = new JdbcTemplate();
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public boolean create(Account account) {
        return false;
    }

    @Override
    public BigDecimal getBalance(long accountId) {
        Account account = null;
        String sql = "SELECT balance FROM account WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);

        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account.getBalance();
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getLong("user_id"));
        account.setAccountId(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }


}