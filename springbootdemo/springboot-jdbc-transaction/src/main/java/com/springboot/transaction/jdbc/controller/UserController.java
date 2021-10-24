package com.springboot.transaction.jdbc.controller;

import com.springboot.transaction.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("addUser")
    public  boolean addUser() {

        return userService.addUser();
    }

}
