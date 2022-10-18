package com.epam.esm.controllers;

import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public UserDto create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto request){
        String email = request.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        UserDto userDto = userService.findByEmail(email);
        String token = tokenProvider.createToken(email, userDto.getRole().name());
        return new AuthenticationResponseDto(email, token);
    }
}
