package com.linksphere.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.linksphere.user_service.security.jwt.JwtProperties;

@SpringBootApplication(scanBasePackages = { "com.linksphere" })
@EnableConfigurationProperties(JwtProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
