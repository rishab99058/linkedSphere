package com.linkedsphere.post_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.linkedsphere.post_service.security.jwt.JwtProperties;

@SpringBootApplication(scanBasePackages = { "com.linksphere", "com.linkedsphere" })
@EnableConfigurationProperties(JwtProperties.class)
@EnableFeignClients
@EnableDiscoveryClient
public class PostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}
