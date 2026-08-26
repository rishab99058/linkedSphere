package com.linksphere.auth_service.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.linksphere.auth_service.security.user.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAutheticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");

    // 1. Authorization header nahi hai
    // 2. Ya Bearer token nahi hai
    // → JWT authentication ka kaam nahi hai, request ko aage bhej do
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // "Bearer " ke 7 characters remove karke actual JWT nikalo
    String token = authHeader.substring(7);

    // JWT se username/email extract karo
    String username = jwtService.extractUsername(token);

    // Agar already authenticated nahi hai
    if (username != null
            && SecurityContextHolder.getContext().getAuthentication() == null) {

        CustomUserDetails user =
                (CustomUserDetails) userDetailsService
                        .loadUserByUsername(username);

        // JWT valid hai to Spring Security ko authenticated user batao
        if (jwtService.isTokenValid(token, user)) {

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

            // Request ki details attach karo
            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // Current request ko authenticated mark karo
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }
    }

    // Authentication hui ho ya nahi, request ko filter chain me aage bhejo
    filterChain.doFilter(request, response);
}
}
