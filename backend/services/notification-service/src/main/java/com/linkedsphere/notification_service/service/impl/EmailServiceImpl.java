package com.linkedsphere.notification_service.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.linkedsphere.notification_service.service.EmailTemplateService;

import jakarta.mail.internet.MimeMessage;

import com.linkedsphere.notification_service.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailTemplateService emailTemplateService;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPasswordResetOtp(String recipientEmail, String otp) {
        String htmlContent = emailTemplateService.renderPasswordResetOtp(
                otp,
                10);
        MimeMessage message = mailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8");

            helper.setFrom(fromEmail, "LinkSphere");

            helper.setTo(recipientEmail);

            helper.setSubject(
                    "Reset your LinkSphere password");

            helper.setText(
                    htmlContent,
                    true);

            mailSender.send(message);

            log.info(
                    "Password reset email sent to {}",
                    recipientEmail);

        } catch (Exception exception) {

            log.error(
                    "Failed to send password reset email to {}",
                    recipientEmail,
                    exception);

            throw new RuntimeException(
                    "Failed to send email: " + exception.getMessage(),
                    exception);
        }
    }

}
