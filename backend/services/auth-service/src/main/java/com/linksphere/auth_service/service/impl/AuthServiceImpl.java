package com.linksphere.auth_service.service.impl;

import com.linksphere.auth_service.dto.request.RegisterRequest;
import com.linksphere.auth_service.dto.response.RegisterResponse;
import com.linksphere.auth_service.service.AuthService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {



     @Override
    public RegisterResponse register(RegisterRequest request) {
        // TODO Auto-generated method stub
       
         throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

 }
