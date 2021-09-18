package com.springboot.transaction.controller;

import com.springboot.transaction.entity.User;
import com.springboot.transaction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("queryUser")
    public List<User> queryUsers() {
        Map<String,Object> params=new HashMap<>();
        params.put("table","user");
        params.put("id",1);
        return userService.findAll(params);
    }

}
