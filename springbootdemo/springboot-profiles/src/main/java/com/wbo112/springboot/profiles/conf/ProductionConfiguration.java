package com.wbo112.springboot.profiles.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("dev")
public class ProductionConfiguration implements MyConfiguration {


    @Override
    public String confName() {
        return "ProductionConfiguration";
    }
}