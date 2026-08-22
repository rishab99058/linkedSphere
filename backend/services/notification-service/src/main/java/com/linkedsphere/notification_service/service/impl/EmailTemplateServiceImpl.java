package com.linkedsphere.notification_service.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.linkedsphere.notification_service.service.EmailTemplateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final TemplateEngine templateEngine;

    @Override
    public String renderPasswordResetOtp(String otp, int expiryMinutes) {
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("expiryMinutes", expiryMinutes);
        return templateEngine.process("password-reset-otp", context);
    }

}
