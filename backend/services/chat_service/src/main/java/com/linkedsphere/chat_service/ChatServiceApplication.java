package com.linkedsphere.chat_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.linkedsphere.chat_service.security.jwt.JwtProperties;

@SpringBootApplication(scanBasePackages = { "com.linksphere", "com.linkedsphere" })
@EnableConfigurationProperties(JwtProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
public class ChatServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}

}
