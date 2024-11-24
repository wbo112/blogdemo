package com.springboot.myhttps.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController {
    @GetMapping("hi")
    public String hi(){
        return "Hello World";
    }
}
