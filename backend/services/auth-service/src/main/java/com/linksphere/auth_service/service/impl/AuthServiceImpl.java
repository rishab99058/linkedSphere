package com.linksphere.auth_service.service.impl;

import com.linksphere.auth_service.dto.request.LoginRequest;
import com.linksphere.auth_service.dto.request.RefreshTokenRequest;
import com.linksphere.auth_service.dto.request.RegisterRequest;
import com.linksphere.auth_service.dto.response.LoginResponse;
import com.linksphere.auth_service.dto.response.RefreshTokenResponse;
import com.linksphere.auth_service.dto.response.RegisterResponse;
import com.linksphere.auth_service.entity.RoleEntity;
import com.linksphere.auth_service.entity.UserEntity;
import com.linksphere.auth_service.entity.UserRole;
import com.linksphere.auth_service.entity.UserRoleId;
import com.linksphere.auth_service.enums.AccountStatus;
import com.linksphere.auth_service.enums.AuthProvider;
import com.linksphere.auth_service.repository.RoleRepository;
import com.linksphere.auth_service.repository.UserRepository;
import com.linksphere.auth_service.security.jwt.JwtProperties;
import com.linksphere.auth_service.security.jwt.JwtService;
import com.linksphere.auth_service.security.refresh.RefreshTokenService;
import com.linksphere.auth_service.security.user.CustomUserDetails;
import com.linksphere.auth_service.service.AuthService;
import com.linksphere.common.enums.ErrorCode;
import com.linksphere.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final JwtProperties jwtProperties;
        private final RefreshTokenService refreshTokenService;

        @Override
        public RegisterResponse register(RegisterRequest request) {

                // 1. Duplicate checks
                if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                        throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
                }

                if (request.getPhoneNumber() != null &&
                                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
                        throw new BaseException(ErrorCode.PHONE_ALREADY_EXISTS);
                }

                // 2. Fetch default role – seeded by Flyway V1 migration
                RoleEntity defaultRole = roleRepository
                                .findByName("ROLE_USER")
                                .orElseThrow(() -> new BaseException(ErrorCode.ROLE_NOT_FOUND));

                // 3. Build user with all required DB fields
                String encodedPassword = passwordEncoder.encode(request.getPassword());

                UserEntity user = UserEntity.builder()
                                .email(request.getEmail())
                                .password(encodedPassword)
                                .phoneNumber(request.getPhoneNumber())
                                .provider(AuthProvider.LOCAL)
                                .emailVerified(false)
                                .accountStatus(AccountStatus.ACTIVE)
                                .deleted(false)
                                .build();

                // 4. Attach role via UserRole join entity (cascades on user save)
                UserRole userRole = UserRole.builder()
                                .id(new UserRoleId(user.getId(), defaultRole.getId()))
                                .role(defaultRole)
                                .user(user)
                                .build();
                user.getUserRoles().add(userRole);

                UserEntity savedUser = userRepository.save(user);

                log.info("Registered new user [id={}, email={}] with role ROLE_USER",
                                savedUser.getId(), savedUser.getEmail());

                // 5. Return response
                return RegisterResponse.builder()
                                .id(savedUser.getId())
                                .email(savedUser.getEmail())
                                .message("User registered successfully")
                                .build();
        }

        @Override
        public LoginResponse login(LoginRequest request) {
                // TODO Auto-generated method stub
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                String accessToken = jwtService.generateAccessToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);

                UUID sessionId = jwtService.extractSessionId(refreshToken);
                refreshTokenService.saveSession(
                                sessionId.toString(),
                                userDetails.getId(),
                                jwtProperties.getAccessTokenExpiration() / 1000);

                return LoginResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .tokenType("Bearer")
                                .expiresIn(jwtProperties.getAccessTokenExpiration() / 1000)
                                .build();
        }

        @Override
        public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
                // TODO Auto-generated method stub
                UUID sessionId = jwtService.extractSessionId(request.getRefreshToken());

                UUID userId = refreshTokenService.getUserId(sessionId.toString());
                if (userId == null) {
                        throw new BaseException(ErrorCode.ACCESS_DENIED);
                }

                UserEntity user = userRepository.findById(userId)
                                .orElseThrow(() -> new BaseException(ErrorCode.ACCESS_DENIED));

                CustomUserDetails userDetails = new CustomUserDetails(user);

                String accessToken = jwtService.generateAccessToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);

                return RefreshTokenResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .tokenType("Bearer")
                                .expiresIn(jwtProperties.getAccessTokenExpiration() / 1000)
                                .build();
        }

        @Override
        public void logout(String refreshToken) {
                // TODO Auto-generated method stub
                UUID sessionId = jwtService.extractSessionId(refreshToken);
                refreshTokenService.deleteSession(sessionId.toString());
        }

}
