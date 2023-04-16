package com.example.pizza_order_demo;


import com.example.pizza_order_demo.model.Topping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;

@SpringBootTest(classes = PizzaOrderDemoApplication.class)
public class MailTest {



    @Test
    public void testMail(){

    }
}
