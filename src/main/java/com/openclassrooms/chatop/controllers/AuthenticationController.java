package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dtos.LoginResponse;
import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.entities.User;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;
import com.openclassrooms.chatop.services.AuthenticationService;
import com.openclassrooms.chatop.services.JwtService;
import com.openclassrooms.chatop.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Validated
@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) throws AlreadyExistException {
        UserResponse registeredUser = userService.createUser(userRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody UserRequest userRequest) throws NotFoundException {
        LoginResponse authenticatedUser = authenticationService.authenticate(userRequest);
        return ResponseEntity.ok(authenticatedUser);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<Object> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object currentUser = authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}