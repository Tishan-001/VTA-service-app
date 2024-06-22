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

    public void bookingSuccess(String recipientEmail, String name, String roomName, String arrivalDate, String departureDate) {
        String subject = "Booking Confirmation - VTA";
        String htmlTextMessage = "<html>" +
                "  <body>" +
                "    <p>" +
                "      Dear " + name + ",<br/><br/>" +
                "      Thank you for choosing our service.<br/><br/>" +
                "      Your booking has been successfully scheduled and is confirmed." +
                "      <br/><h6>Please note that this email keep your.</h6>" +
                "      <br/>" +
                "      Here are your Booking Details:<br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Room Name:</font>" +
                "      <font style=\"color:green;font-weight:bold;\">" + roomName + "</font><br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Arrival Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                arrivalDate + "</font><br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Departure Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                departureDate + "</font><br/>" +
                "      <br/>" + "<br/>" +
                "      Thank you for your trust in us.<br/><br/>" +
                "      We look forward to serving you.<br/><br/> <font style=\"color:green;font-weight:bold;\">VTA service</font>" +
                "    </p>" +
                "  </body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@vta.com");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(htmlTextMessage, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void tourguideBooking(String recipientEmail, String name, String guiderName, String startDate, String startTime, String endDate, String endTime) {
        String subject = "Booking Confirmation - VTA";
        String htmlTextMessage = "<html>" +
                "  <body>" +
                "    <p>" +
                "      Dear " + name + ",<br/><br/>" +
                "      Thank you for choosing our service.<br/><br/>" +
                "      Your booking has been successfully scheduled and is confirmed." +
                "      <br/><h6>Please note that this email keep your.</h6>" +
                "      <br/>" +
                "      Here are your Booking Details:<br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Room Name:</font>" +
                "      <font style=\"color:green;font-weight:bold;\">" + guiderName + "</font><br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Arrival Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                startDate + "," + startTime + "</font><br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Departure Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                endDate + "," + endTime + "</font><br/>" +
                "      <br/>" + "<br/>" +
                "      Thank you for your trust in us.<br/><br/>" +
                "      We look forward to serving you.<br/><br/> <font style=\"color:green;font-weight:bold;\">VTA service</font>" +
                "    </p>" +
                "  </body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@vta.com");
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(htmlTextMessage, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
