package com.bootx.service.impl;

import com.bootx.service.EmailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class EmailServiceImpl implements EmailService {

    public boolean send(String toEmail,String code){
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.163.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("blackboy1987@163.com", "Parent19871006"));
            email.setSSLOnConnect(true);
            email.setFrom("blackboy1987@163.com");
            email.setSubject("【一枚猿】邮箱验证");
            email.setMsg("您正在申请获取邮箱验证码，验证码为："+code+"，验证码有效期为10分钟");
            email.addTo(toEmail);
            email.send();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
