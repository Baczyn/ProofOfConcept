package com.proof.of.concept.frontend.security.service;

import com.proof.of.concept.frontend.security.exception.UserAlreadyExistException;
import com.proof.of.concept.frontend.security.exception.UserNotFoundException;
import com.proof.of.concept.frontend.security.model.User;
import com.proof.of.concept.frontend.security.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@Named
public class SecurityService {

    @Inject
    private UserRepository repository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    public User create(User userRequest) throws UserAlreadyExistException {

        if (repository.getByName(userRequest.getName()).isPresent())
            throw new UserAlreadyExistException("There is an user with this user name: " + userRequest.getName());

        User user = User.builder()
                .withPasswordHash(passwordHash)
                .withPassword(userRequest.getPassword())
                .withName(userRequest.getName())
                .withRoles(userRequest.getRoles())
                .build();
        return User.fromJson(repository.create(user));
    }

    public User login(User userRequest) throws AuthenticationException {

        String userJson = repository.getByName(userRequest.getName()).
                orElseThrow(() -> new UserNotFoundException(userRequest.getName()));

        User user = User.fromJson(userJson);

        if (!passwordHash.verify(userRequest.getPassword().toCharArray(), user.getPassword())) {
            throw new AuthenticationException();
        }
        return user;
    }
}
