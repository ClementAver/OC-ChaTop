package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;

public interface UserInterface {
    UserResponse getUser(Integer id) throws NotFoundException;
    UserResponse createUser(UserRequest userRequest) throws AlreadyExistException;
    UserResponse getUserByEmail(String email) throws NotFoundException;
}
