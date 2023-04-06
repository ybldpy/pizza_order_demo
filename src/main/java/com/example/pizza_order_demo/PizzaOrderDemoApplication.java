package com.example.pizza_order_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.example.pizza_order_demo.mapper")
@EnableTransactionManagement
public class PizzaOrderDemoApplication {

    public static ApplicationContext app;

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(PizzaOrderDemoApplication.class, args);
        app = ac;
//        String config = PizzaOrderDemoApplication.class.getClassLoader().getResource("generatorConfig.xml").getFile();
//        String[] arg = {"-configfile",config,"-overwrite"};
//
//        ShellRunner.main(arg);

    }

}
