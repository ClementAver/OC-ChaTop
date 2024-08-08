package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dtos.LoginResponse;
import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.entities.User;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.services.AuthenticationService;
import com.openclassrooms.chatop.services.JwtService;
import com.openclassrooms.chatop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@Validated
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) throws AlreadyExistException {
        UserResponse registeredUser = userService.createUser(userRequest);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody UserRequest userRequest) {
        User authenticatedUser = authenticationService.authenticate(userRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}