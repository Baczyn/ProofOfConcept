package com.proof.of.concept.frontend.security.service;

import com.proof.of.concept.frontend.security.exception.UserAlreadyExistException;
import com.proof.of.concept.frontend.security.exception.UserNotAuthorizedException;
import com.proof.of.concept.frontend.security.exception.UserNotFoundException;
import com.proof.of.concept.frontend.security.model.User;
import com.proof.of.concept.frontend.security.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.security.Principal;

@ApplicationScoped
@Named
public class SecurityService {

    @Inject
    private UserRepository repository;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    private SecurityContext securityContext;

    public User create(User userRequest) throws UserAlreadyExistException {

        if(repository.getByName(userRequest.getName()).isPresent())
                throw new  UserAlreadyExistException("There is an user with this user name: " + userRequest.getName());

        User user = User.builder()
                .withPasswordHash(passwordHash)
                .withPassword(userRequest.getPassword())
                .withName(userRequest.getName())
                .withRoles(userRequest.getRoles())
                .build();
        return  User.fromJson(repository.create(user));
    }


    public User login(User userRequest) throws AuthenticationException {

        String userJson = repository.getByName(userRequest.getName()).
                orElseThrow(() -> new UserNotFoundException(userRequest.getName()));

        System.out.println(userJson);
        User user = User.fromJson(userJson);

        if (!passwordHash.verify(userRequest.getPassword().toCharArray(), user.getPassword())) {
            throw new AuthenticationException();
        }

        return user;
    }

    void delete(String userId) {
        repository.deleteById(userId);
    }

    //    void updatePassword(String id, UserDTO dto) {
//
//        final Principal principal = securityContext.getCallerPrincipal();
//        if (isForbidden(id, securityContext, principal)) {
//            throw new UserForbiddenException();
//        }
//
//        final User user = repository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException(id));
//        user.updatePassword(dto.getPassword(), passwordHash);
//        repository.save(user);
//    }
//
//
    public User getUser() {
        final Principal principal = securityContext.getCallerPrincipal();
        System.out.println(principal);
        if (principal == null) {
            System.out.println("UserNotAuthorizedException");
            throw new UserNotAuthorizedException();
        }
        final String userJson = repository.getByName(principal.getName())
                .orElseThrow(() -> new UserNotFoundException(principal.getName()));
        System.out.println(userJson);
        return User.fromJson(userJson);
    }
//
//    public List<UserDTO> getUsers() {
//        return repository.findAll()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    private UserDTO toDTO(User user) {
//        UserDTO dto = new UserDTO();
//        dto.setName(user.getName());
//        dto.setRoles(getRole());
//        return dto;
//    }
//
//    private boolean isForbidden(String id, SecurityContext context, Principal principal) {
//        return !(context.isCallerInRole(Role.ADMIN.name()) || id.equals(principal.getName()));
//    }
}
