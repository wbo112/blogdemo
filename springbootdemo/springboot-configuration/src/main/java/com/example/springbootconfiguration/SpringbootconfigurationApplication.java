package com.example.springbootconfiguration;

import com.example.springbootconfiguration.entity.Home;
import com.example.springbootconfiguration.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootconfigurationApplication  {


    public static void main(String[] args) {
        String p=SpringbootconfigurationApplication.class.getClassLoader().getResource("").getPath()+"generate-class";
        //设置生成代理类的class文件保存到本地位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,p);
        SpringApplication.run(SpringbootconfigurationApplication.class, args);
    }

    @Bean
    public User getUser() {
        return new User();
    }

    @Bean
    public Home getHome() {
        return new Home(getUser());
    }

}
