package com.vta.vtabackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSender mailSender;

    public void sendOtp(String otp, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@vta.com");
        message.setTo(email);
        message.setSubject("Email Verification Code");
        message.setText("Your verification code is "+ otp);
        System.out.println(message.getText());
        mailSender.send(message);
    }
}
