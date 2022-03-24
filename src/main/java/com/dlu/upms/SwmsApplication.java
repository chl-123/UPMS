package com.dlu.upms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.dlu.upms.*.mapper")
@ComponentScan(basePackages = "com.dlu.upms.*.service")
@ComponentScan(basePackages = "com.dlu.upms.*.aspect")
@ComponentScan(basePackages = "com.dlu.upms.*.component")
@ComponentScan(basePackages = "com.dlu.upms.*.controller")
public class SwmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwmsApplication.class, args);
    }

}
