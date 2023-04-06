package com.example.pizza_order_demo.utils;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;



@Component
public class MailUtil {


    private static JavaMailSender javaMailSender;
    private static String[] sender = {"18508782239@163.com"};

    public MailUtil(JavaMailSender mailSender){
        javaMailSender = mailSender;
    }

    private static Random random = new Random();

    public static String createValidationCode(int i){

        int len = i>=4?i:4;
        StringBuilder sb = new StringBuilder();

        for(int index = 0;index<len;index++){
            sb.append(random.nextInt(10));
        }

        return sb.toString();

    }

    public static boolean isValidateMail(String mail){
        return mail.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }


    public static boolean sendCode(String code,String mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("Verification Code");
        simpleMailMessage.setText(code);
        for(String s:sender){
            try {
                simpleMailMessage.setFrom(s);
                javaMailSender.send(simpleMailMessage);
                return true;
            }
            catch (MailException e){
                e.printStackTrace();
            }
        }

        return false;

    }


}
