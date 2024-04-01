package com.vta.vtabackend.services;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    public void sendOtp(String otp, String email) {

        String subject = "Email Verification Code";
        String htmlContent = "<html>" +
                "<body style='text-align: center; font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #4A90E2;'>Your Verification Code</h2>" +
                "<p style='font-size: 16px; color: #333;'>Use the following OTP to complete your verification:</p>" +
                "<div style='margin: 20px;'>" +
                "<span style='background-color: #F7F9FA; padding: 8px 12px; border-radius: 4px; border: 1px solid #D0D4D7; font-size: 24px; font-weight: bold; color: #333;'>" +
                otp +
                "</span>" +
                "</div>" +
                "<p style='font-size: 14px; color: #555;'>This code is valid for 5 minutes. Do not share this OTP with anyone.</p>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@vta.com");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
