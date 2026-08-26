package com.linksphere.auth_service.security.user;

import com.linksphere.auth_service.entity.UserEntity;
import com.linksphere.auth_service.enums.AccountStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    public UUID getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getUserRoles().stream().map((e) -> new SimpleGrantedAuthority(e.getRole().getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return !Boolean.TRUE.equals(user.getDeleted())
                && user.getAccountStatus() == AccountStatus.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Boolean.TRUE.equals(user.getDeleted());
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Boolean.TRUE.equals(user.getDeleted());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !Boolean.TRUE.equals(user.getDeleted());
    }

}
