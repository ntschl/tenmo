package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    public static List<Account> accounts = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
        String sql = "SELECT balance, user_id, account_id FROM account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if(results.next()){
            account = mapRowToAccount(results);
        }
        return account.getBalance();
    }

    public List<Account> getAccountsByUsername(String username){
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, tu.user_id, balance" +
                    " FROM account AS a JOIN tenmo_user AS tu ON tu.user_id = a.user_id" +
                    " WHERE username = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while(results.next()){
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setUserId(rs.getLong("user_id"));
        account.setAccountId(rs.getLong("account_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }


}
