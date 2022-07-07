package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao accountDao){
        dao = accountDao;
    }

    @RequestMapping(path = "/account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id){
        return dao.getBalance(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(path = "/send/{id}/{amount}", method = RequestMethod.PUT)
    public boolean sendFunds(@PathVariable int id, @PathVariable BigDecimal amount) {
        return dao.sendFunds(id, amount);
    }


}



