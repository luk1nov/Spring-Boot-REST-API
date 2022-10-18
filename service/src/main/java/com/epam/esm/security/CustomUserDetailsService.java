package com.epam.esm.security;

import com.epam.esm.model.User;
import com.epam.esm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private final SecurityUserDetailsBuilder userDetailsBuilder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userDetailsBuilder.create(user);
    }
}
