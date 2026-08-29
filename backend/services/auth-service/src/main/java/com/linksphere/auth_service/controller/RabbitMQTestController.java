package com.linksphere.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linksphere.auth_service.rabbitmq.UserEventPublisher;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQTestController {

    @Autowired
    private UserEventPublisher userEventPublisher;

    @PostMapping
    public String sendMessage() {

        userEventPublisher.publishUserCreated(
                "Hello RabbitMQ");

        return "Message sent successfully";
    }

}
