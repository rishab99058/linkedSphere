package com.linkedsphere.notification_service.config;

import com.linksphere.common.constants.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange linksphereExchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(RabbitMQConstants.USER_QUEUE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(RabbitMQConstants.NOTIFICATION_QUEUE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstants.EMAIL_QUEUE);
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.USER_CREATED);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.NOTIFICATION_CREATED);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.EMAIL_SEND);
    }
}
