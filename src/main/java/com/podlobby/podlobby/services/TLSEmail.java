package com.podlobby.podlobby.services;
import com.podlobby.podlobby.services.EmailUtil;
import com.podlobby.podlobby.util.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//Encrypts & delivers mail securely
@Service
public class TLSEmail {
    public String fromEmail = "podlobby@gmail.com"; //requires valid gmail id

    @Value("${mail.password}")
    public String password; // correct password for gmail id

    public void sendEmail(
            final String toEmail, String userName, String subject, String content, boolean forgotPassword) throws ServletException, IOException  // can be any email id
    {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "465"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        if(forgotPassword) {
            String randomPassword = Password.randomGen();
            EmailUtil.sendEmail(session, toEmail, "Forgot Password", "Hello " + userName + " your temporary password is " + randomPassword + " please go to http://podlobby/newPassword");
        } else {
            EmailUtil.sendEmail(session, toEmail, subject, content);
        }
    }



}