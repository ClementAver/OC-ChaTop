package com.openclassrooms.chatop.controllers;

import com.openclassrooms.chatop.dtos.RentalRequest;
import com.openclassrooms.chatop.dtos.RentalResponse;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;
import com.openclassrooms.chatop.services.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/rentals", consumes = APPLICATION_JSON_VALUE)
    public RentalResponse createRental(@Valid @RequestBody RentalRequest rentalRequest) throws AlreadyExistException {
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/rentals")
    public Stream<RentalResponse> getRentals() {
        return rentalService.getRentals();
    }

    @GetMapping("/rentals/{id}")
    public RentalResponse getRental(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id) throws NotFoundException {
        return rentalService.getRental(id);
    }

    @PutMapping("/rentals/{id}")
    public RentalResponse updateRental(@PathVariable @Min(value = 1, message = "L'identifiant doit être égal ou supérieur à un (1).") int id, @Valid @RequestBody RentalRequest rentalRequest) throws NotFoundException {
        return rentalService.updateRental(id, rentalRequest);
    }
}
