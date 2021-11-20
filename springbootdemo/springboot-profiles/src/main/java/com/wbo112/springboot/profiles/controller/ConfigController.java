package com.wbo112.springboot.profiles.controller;

import com.wbo112.springboot.profiles.conf.MyConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {
    @Autowired
    private MyConfiguration myConfiguration;
    @GetMapping("confName")
    public String confName(){
        return  myConfiguration.confName();
    }
}
