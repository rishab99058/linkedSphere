package com.linkedsphere.notification_service.service;

public interface EmailTemplateService {

    String renderPasswordResetOtp(
            String otp,
            int expiryMinutes);

}
