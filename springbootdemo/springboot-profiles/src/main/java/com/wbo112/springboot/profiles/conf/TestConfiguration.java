package com.wbo112.springboot.profiles.conf;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("test")
public class TestConfiguration implements MyConfiguration {


    @Override
    public String confName() {
        return "TestConfiguration";
    }
}