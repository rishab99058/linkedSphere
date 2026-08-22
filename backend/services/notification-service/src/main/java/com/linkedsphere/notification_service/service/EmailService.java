package com.linkedsphere.notification_service.service;

public interface EmailService {

    void sendPasswordResetOtp(
            String recipientEmail,
            String otp);

}
