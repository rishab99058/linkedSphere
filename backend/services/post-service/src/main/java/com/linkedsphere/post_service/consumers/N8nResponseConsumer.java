package com.linkedsphere.post_service.consumers;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedsphere.post_service.service.AiCaptionService;
import com.linksphere.common.constants.RabbitMQConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class N8nResponseConsumer {

    private final ObjectMapper objectMapper;
    private final AiCaptionService aiCaptionService;

    @RabbitListener(queues = RabbitMQConstants.N8N_RESPONSE_QUEUE)
    public void consumeN8nResponse(Object rawMessage) {
        String payloadString = "";

        if (rawMessage instanceof Message amqpMessage) {
            payloadString = new String(amqpMessage.getBody(), StandardCharsets.UTF_8);
        } else if (rawMessage instanceof byte[] bytes) {
            payloadString = new String(bytes, StandardCharsets.UTF_8);
        } else if (rawMessage instanceof String str) {
            payloadString = str;
        } else {
            try {
                payloadString = objectMapper.writeValueAsString(rawMessage);
            } catch (Exception e) {
                payloadString = String.valueOf(rawMessage);
            }
        }

        // Pretty print if JSON
        String formattedOutput = payloadString;
        try {
            Object jsonObject = objectMapper.readValue(payloadString, Object.class);
            formattedOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (Exception ignored) {
            // keep raw string if not JSON
        }

        log.info("📩 [n8n Async Response Received]:\n{}", formattedOutput);
        System.out.println("=================================================");
        System.out.println("🎉 [n8n Response Received in Consumer]:");
        System.out.println(formattedOutput);
        System.out.println("=================================================");

        // Hand over to service to resolve pending request
        aiCaptionService.handleN8nResponse(payloadString);
    }
}

