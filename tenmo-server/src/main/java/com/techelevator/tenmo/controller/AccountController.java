package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {

    private AccountDao dao;

    public AccountController(){
        dao = new JdbcAccountDao();
    }

    @RequestMapping(path = "/account/{id}/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int id){
        return dao.getBalance(id);
    }


}



