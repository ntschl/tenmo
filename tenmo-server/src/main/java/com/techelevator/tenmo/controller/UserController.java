package com.techelevator.tenmo.controller;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao dao;

    public UserController(UserDao userDao) {
        dao = userDao;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> findUsers(){
        return dao.findUsernames();
    }

}
