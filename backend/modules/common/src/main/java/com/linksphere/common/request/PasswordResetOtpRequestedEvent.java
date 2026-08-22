package com.linksphere.common.request;

import java.time.Instant;
import java.util.UUID;

public record PasswordResetOtpRequestedEvent(
                UUID userId,
                String recipientEmail,
                String otp,
                Instant expiresAt) {
}
