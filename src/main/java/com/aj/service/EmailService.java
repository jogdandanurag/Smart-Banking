package com.aj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url:http://localhost:7725}")
    private String baseUrl;

    @Value("${app.email.enabled:false}")
    private boolean emailEnabled;

    public void sendResetPasswordEmail(String toEmail, String resetToken) {
        String resetLink = baseUrl + "/api/auth/reset-password?token=" + resetToken;
        String emailBody = "<h1>Reset Your Password</h1>" +
                "<p>You requested a password reset. Click the link below to proceed:</p>" +
                "<a href='" + resetLink + "'>Reset Password</a>" +
                "<p>This link expires in 15 minutes. If you didn’t request this, ignore this email.</p>";

        if (emailEnabled) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setTo(toEmail);
                helper.setSubject("Password Reset Request - Your Application");
                helper.setText(emailBody, true);
                mailSender.send(message);
                logger.info("Reset password email sent to: {} with reset link: {}", toEmail, resetLink);
            } catch (MessagingException e) {
                logger.error("Failed to send reset email to {}: {}", toEmail, e.getMessage());
                throw new RuntimeException("Failed to send reset email", e);
            } catch (Exception e) {
                logger.error("Unexpected error sending reset email to {}: {}", toEmail, e.getMessage());
                throw new RuntimeException("Unexpected error occurred while sending email", e);
            }
        } else {
            logger.info("Email sending disabled (local mode). Reset link for {}: {}", toEmail, resetLink);
        }
    }
}