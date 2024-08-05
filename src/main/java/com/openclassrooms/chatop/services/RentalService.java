package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.RentalRequest;
import com.openclassrooms.chatop.dtos.RentalResponse;
import com.openclassrooms.chatop.entities.Rental;
import com.openclassrooms.chatop.entities.User;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;
import com.openclassrooms.chatop.mappers.RentalDTOMapper;
import com.openclassrooms.chatop.repositories.RentalRepository;
import com.openclassrooms.chatop.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RentalService implements RentalInterface{

    private final RentalRepository rentalRepository;
    private final RentalDTOMapper rentalDTOMapper;
    private final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, RentalDTOMapper rentalDTOMapper, UserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.rentalDTOMapper = rentalDTOMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RentalResponse createRental(RentalRequest rentalRequest) throws AlreadyExistException, NotFoundException {
        Optional<Rental> rentalInDB = rentalRepository.findByName(rentalRequest.getName());
        if (rentalInDB.isPresent()) {
            throw new AlreadyExistException("Ce nom n'est plus disponible.");
        }

        Rental rental = new Rental();

        Optional<User> userInDB = userRepository.findById(rentalRequest.getOwner_id());
        if (userInDB.isPresent()) {
            rental.setOwner(userInDB.get());
        } else {
            throw new NotFoundException("Utilisateur non référencé.");
        }

        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setPicture(rentalRequest.getPicture());
        rental.setDescription(rentalRequest.getDescription());

        rental.setCreatedAt(LocalDate.now());
        rental.setUpdatedAt(LocalDate.now());

        rentalRepository.save(rental);
        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }

    @Override
    public Stream<RentalResponse> getRentals() {
        return rentalRepository.findAll()
                .stream().map(rentalDTOMapper);
    }

    @Override
    public RentalResponse getRental(Integer id) throws NotFoundException {
        Optional<Rental> rentalInDB = rentalRepository.findById(id);
        if(rentalInDB.isPresent()) {
            Rental rental = rentalInDB.get();
            return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
        } else {
            throw new NotFoundException("Location non référencée.");
        }
    }

    @Override
    public RentalResponse updateRental(Integer id, RentalRequest rentalRequest) throws NotFoundException {
        Optional<Rental> rentalInDB = rentalRepository.findById(id);

        User user = null;
        if (rentalRequest.getOwner_id() != null) {
            Optional<User> userInDB = userRepository.findById(rentalRequest.getOwner_id());
            if (userInDB.isPresent()) {
                user = userInDB.get();
            } else {
                throw new NotFoundException("Utilisateur non référencé.");
            }
        }

        if (rentalInDB.isPresent()) {
            Rental rental = rentalInDB.get();
            if (rentalRequest.getName() != null) {
                rental.setName(rentalRequest.getName());
            }
            if (rentalRequest.getSurface() != null) {
                rental.setSurface(rentalRequest.getSurface());
            }
            if (rentalRequest.getPrice() != null) {
                rental.setPrice(rentalRequest.getPrice());
            }
            if (rentalRequest.getPicture() != null) {
                rental.setPicture(rentalRequest.getPicture());
            }
            if (rentalRequest.getDescription() != null) {
                rental.setDescription(rentalRequest.getDescription());
            }
            if (user != null) {
                rental.setOwner(user);
            }
            rental.setUpdatedAt(LocalDate.now());

            rentalRepository.save(rental);
            return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
        } else {
            throw new NotFoundException("Location non référencée.");
        }
    }
}
