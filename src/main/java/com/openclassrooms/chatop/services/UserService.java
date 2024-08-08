package com.openclassrooms.chatop.services;

import com.openclassrooms.chatop.dtos.UserRequest;
import com.openclassrooms.chatop.dtos.UserResponse;
import com.openclassrooms.chatop.entities.User;
import com.openclassrooms.chatop.exceptions.AlreadyExistException;
import com.openclassrooms.chatop.exceptions.NotFoundException;
import com.openclassrooms.chatop.mappers.UserDTOMapper;
import com.openclassrooms.chatop.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    // private final UserDTOMapper userDTOMapper;


    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        // this.userDTOMapper = userDTOMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserResponse getUser(Integer id) throws NotFoundException {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isPresent()) {
            User user = userInDB.get();
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        } else {
            throw new NotFoundException("Utilisateur non référencé.");
        }
    }

    @Override
    public UserResponse getUserByEmail(String email) throws NotFoundException {
        Optional<User> userInDB = userRepository.findByEmail(email);
        if (userInDB.isPresent()) {
            User user = userInDB.get();
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        } else {
            throw new NotFoundException("Utilisateur non référencé.");
        }
    }

    // Sign-up
    @Override
    public UserResponse createUser(UserRequest userRequest) throws AlreadyExistException {
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isPresent()) {
            throw new AlreadyExistException("Cet email a déjà été renseigné.");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        userRepository.save(user);

        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
