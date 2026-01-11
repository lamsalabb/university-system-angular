package com.university.core.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserCredentials(String to, String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your University Account Credentials");
        message.setText("""
                Your account has been created by the administrator.
                
                Login Email: %s
                Password: %s
                
                Please log in and change your password from your profile.
                """.formatted(to, password)
        );

        mailSender.send(message);
    }
}
