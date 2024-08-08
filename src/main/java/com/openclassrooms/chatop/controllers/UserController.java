package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;
import com.openclassrooms.chatop.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // Replaced by the "api/auth/register" route.
//    @ResponseStatus(value = HttpStatus.CREATED)
//    @PostMapping(value = "/user", consumes = APPLICATION_JSON_VALUE)
//    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) throws AlreadyExistException {
//        return userService.createUser(userRequest);
//    }

    @GetMapping("/user/{id}")
    public UserResponse getUser(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
        return userService.getUser(id);
    }
}
