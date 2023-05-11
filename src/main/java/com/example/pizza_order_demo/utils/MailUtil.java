package com.example.pizza_order_demo.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.Simple;
import com.tencentcloudapi.ses.v20201002.models.Template;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;



@Component
public class MailUtil {


    private static JavaMailSender javaMailSender;
    private static String sender = "18508782239@163.com";

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


    public static boolean sendCode(String secretId,String secretKey,String code,String mail) throws TencentCloudSDKException {
        Credential credential = new Credential(secretId,secretKey);
        SesClient sesClient = new SesClient(credential,"ap-guangzhou");
        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSubject("Verification Code");
        sendEmailRequest.setFromEmailAddress("CPT202-Group28@mail.jcy.icu");
        Template template = new Template();
        template.setTemplateID(21127L);
        template.setTemplateData(String.format("{\"code\":\"%s\"}",code));
        sendEmailRequest.setTemplate(template);
        sendEmailRequest.setDestination(new String[]{mail});
        sesClient.SendEmail(sendEmailRequest);
        return true;
    }


}
