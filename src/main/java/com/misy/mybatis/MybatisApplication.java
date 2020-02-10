package com.misy.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.misy.mybatis.mapper")
public class MybatisApplication {

    private static ApplicationContext applicationContext;

    public static synchronized ApplicationContext getInstance(){
        return applicationContext;
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(MybatisApplication.class, args);
        MybatisApplication.setApplicationContext(applicationContext);
    }

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}
