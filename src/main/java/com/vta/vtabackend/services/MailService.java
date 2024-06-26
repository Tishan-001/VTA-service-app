package com.vta.vtabackend.services;

import com.vta.vtabackend.documents.Users;
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
    private final TokenService tokenService;

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

    public void sendForgotPasswordEmail(Users user) {
        String token = tokenService.generateToken(user);

        String subject = "Password Reset Request for VTA System";

        String htmlTextMessage = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #007bff;'>Dear " + user.getName() + ",</h2>" +
                "<p>We have received a request to reset the password for your VTA System account.</p>" +
                "<p>To proceed with the password reset, please click on the button below:</p>" +
                "<table cellpadding='0' cellspacing='0' border='0'>" +
                "<tr>" +
                "<td align='center' bgcolor='#007bff' style='border-radius: 3px;'>" +
                ""+generateForgotPasswordLink(token)+"</td>" +
                "</td>" +
                "</tr>" +
                "</table>" +
                "<p>If you did not initiate this password reset request, please disregard this email.</p>" +
                "<p>Best Regards,<br/>VTA System Administration</p>" +
                "</body>" +
                "</html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("noreply@vta.com");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(htmlTextMessage, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateForgotPasswordLink(String token) {
        return "<a href='https://vtarilankaweb.vercel.app/newpassword?token=" + token + "' target='_blank' style='font-size: 16px; font-family: Arial, sans-serif; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 3px; display: inline-block; background-color: #007bff;'>Reset Password</a>";
    }

    public void transportBooking(String recipientEmail, String name, String startDate, String endDate, String price, String pickupLocation, String dropLocation, String ownerCantactNo) {
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
                "      <font style=\"color:red;font-weight:bold;\">Start Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                startDate + "</font><br/>" +
                "      <br/>" +
                "      <font style=\"color:red;font-weight:bold;\">End Date:</font> <font style=\"color:green;font-weight:bold;\">" +
                endDate + "</font><br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Booking Price:</font> <font style=\"color:green;font-weight:bold;\">" +
                price + "</font><br/>" +
                "      <font style=\"color:red;font-weight:bold;\">PickUp Location:</font> <font style=\"color:green;font-weight:bold;\">" +
                pickupLocation + "</font><br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Drop Location:</font> <font style=\"color:green;font-weight:bold;\">" +
                dropLocation + "</font><br/>" +
                "      <font style=\"color:red;font-weight:bold;\">Owner Contact Number:</font> <font style=\"color:green;font-weight:bold;\">" +
                ownerCantactNo + "</font><br/>" +
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
