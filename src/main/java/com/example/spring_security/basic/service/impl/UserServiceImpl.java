package com.example.spring_security.basic.service.impl;

import com.example.spring_security.basic.entity.User;
import com.example.spring_security.basic.jwt.JwtTokenProvider;
import com.example.spring_security.basic.payload.LoginRequest;
import com.example.spring_security.basic.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl {
    AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    private JwtTokenProvider tokenProvider;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken((User) authentication.getPrincipal());
        return new LoginResponse(jwt);
    }
}
