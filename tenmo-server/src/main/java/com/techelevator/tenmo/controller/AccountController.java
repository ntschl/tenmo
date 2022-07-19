package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransactionDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao accountDao){
        dao = accountDao;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> getAccountsByUsername(Principal principal){
       return dao.getAccountsByUsername(principal.getName());
    }

}



