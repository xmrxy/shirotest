package com.wu.shirotest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@MapperScan(basePackages = "com.wu.shirotest.dao")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(value = "com.wu.shirotest")
@ServletComponentScan
@EnableScheduling
public class ShirotestApplication {


    // nohup
    public static void main(String[] args) {
        SpringApplication.run(ShirotestApplication.class, args);
    }

}
