package com.example.pizza_order_demo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(classes = PizzaOrderDemoApplication.class)
public class MailTest {


    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testMail(){


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18508782239@163.com");
        simpleMailMessage.setTo("Chunyu.Jiang20@student.xjtlu.edu.cn");
        simpleMailMessage.setSubject("code");
        simpleMailMessage.setText("1234");

        mailSender.send(simpleMailMessage);



    }
}
