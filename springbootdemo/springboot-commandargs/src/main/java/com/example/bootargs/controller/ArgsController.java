package com.example.bootargs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Set;

//@RestController

public class ArgsController {


    @Autowired
    private ApplicationArguments springApplicationArguments;

    @GetMapping("/printArgs")
    public String printArgs() {

        System.out.println(springApplicationArguments.getNonOptionArgs());
        Set<String> optionNames = springApplicationArguments.getOptionNames();
        for (String optionName : optionNames) {
            System.out.println(springApplicationArguments.getOptionValues(optionName));
        }
        return Arrays.toString(springApplicationArguments.getSourceArgs());
    }


}
