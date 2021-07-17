package com.example.springbootmybatis.controller;

import com.example.springbootmybatis.entity.User;
import com.example.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("queryUser")
    public List<User> queryUsers() {
        return userService.findAll();
    }

}
