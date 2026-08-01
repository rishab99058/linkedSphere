package com.linksphere.auth_service.service;

import com.linksphere.auth_service.dto.request.RegisterRequest;
import com.linksphere.auth_service.dto.response.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest request);

}
