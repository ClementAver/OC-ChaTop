package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.RentalRequest;
import com.openclassrooms.chatop.dtos.RentalResponse;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;

import java.io.IOException;
import java.util.stream.Stream;

public interface RentalInterface {
    Stream<RentalResponse> getRentals();
    RentalResponse getRental(Integer id) throws NotFoundException;
    RentalResponse createRental(RentalRequest rentalRequest) throws AlreadyExistException, NotFoundException, IOException;
    RentalResponse updateRental(Integer id, RentalRequest rentalRequest) throws NotFoundException;
}
