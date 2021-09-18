package com.springboot.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication

public class SpringbootTransactionApplication {

    public static void main(String[] args) {
        String p=SpringbootTransactionApplication.class.getClassLoader().getResource("").getPath()+"generate-class";
        //设置生成代理类的class文件保存到本地位置
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,p);
        SpringApplication.run(SpringbootTransactionApplication.class, args);
    }

}
