package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.MessageRequest;
import com.openclassrooms.chatop.dtos.MessageResponse;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;

public interface MessageInterface {
    MessageResponse getMessage(Integer id) throws NotFoundException;
    MessageResponse createMessage(MessageRequest messageRequest) throws AlreadyExistException, NotFoundException;
}
