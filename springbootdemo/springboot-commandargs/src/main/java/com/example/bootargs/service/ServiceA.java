package com.example.bootargs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceA {
    @Autowired
    private ServiceB serviceB;
}
