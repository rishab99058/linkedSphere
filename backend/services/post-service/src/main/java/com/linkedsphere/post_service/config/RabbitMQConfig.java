package com.linkedsphere.post_service.config;

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
    public Queue n8nQueue() {
        return new Queue(RabbitMQConstants.N8N_QUEUE);
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
    public Queue n8nResponseQueue() {
        return new Queue(RabbitMQConstants.N8N_RESPONSE_QUEUE);
    }

    @Bean
    public Binding n8nBinding() {
        return BindingBuilder.bind(n8nQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.N8N_TRIGGER_RECEIVED);
    }

    @Bean
    public Binding n8nResponseBinding() {
        return BindingBuilder.bind(n8nResponseQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.N8N_RESPONSE_RECEIVED);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.NOTIFICATION_CREATED);
    }

    @Bean
    public Queue userSyncQueue() {
        return new Queue(RabbitMQConstants.USER_SYNC_QUEUE);
    }

    @Bean
    public Binding userSyncBinding() {
        return BindingBuilder.bind(userSyncQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.USER_SYNC);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(linksphereExchange())
                .with(RabbitMQConstants.EMAIL_SEND);
    }

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }

    @Bean
    public org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate(
            org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        org.springframework.amqp.rabbit.core.RabbitTemplate template = 
                new org.springframework.amqp.rabbit.core.RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

}
