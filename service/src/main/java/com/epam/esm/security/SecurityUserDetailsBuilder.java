package com.epam.esm.security;

import com.epam.esm.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SecurityUserDetailsBuilder {
    private static final boolean ACTIVE = true;

    public JwtUser create(User user){
        return JwtUser.builder()
                .userId(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(new HashSet<>(user.getRole().getAuthorities()))
                .isActive(ACTIVE)
                .build();
    }
}
