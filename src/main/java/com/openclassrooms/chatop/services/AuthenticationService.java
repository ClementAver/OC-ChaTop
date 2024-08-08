package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.entities.User;
import com.openclassrooms.chatop.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        return userRepository.findByEmail(userRequest.getEmail())
                .orElseThrow();
    }
}