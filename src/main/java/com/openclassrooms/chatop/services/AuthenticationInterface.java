package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.LoginResponse;
import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.exceptions.NotFoundException;

public interface AuthenticationInterface {
    LoginResponse authenticate(UserRequest userRequest) throws NotFoundException;
}
