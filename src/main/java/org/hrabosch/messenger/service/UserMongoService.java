package org.hrabosch.messenger.service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hrabosch.messenger.entity.MessengerUser;
import org.hrabosch.messenger.exception.UserAlreadyExistsException;
import org.hrabosch.messenger.exception.UsernameNotFoundException;
import org.hrabosch.messenger.model.login.UserLoginRequest;
import org.hrabosch.messenger.model.login.UserLoginResponse;
import org.hrabosch.messenger.model.registration.UserRegistrationRequest;
import org.hrabosch.messenger.model.registration.UserRegistrationResponse;
import org.hrabosch.messenger.repository.MessengerUserRepository;
import org.hrabosch.messenger.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMongoService implements UserService {

    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private MessengerUserRepository messengerUserRepository;

    @Autowired
    public UserMongoService(PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager, MessengerUserRepository messengerUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.messengerUserRepository = messengerUserRepository;
    }

    @Override
    public UserRegistrationResponse registerUser(UserRegistrationRequest registrationRequest) {
        if(messengerUserRepository.findByUsername(registrationRequest.getUsername()) != null)
            throw new UserAlreadyExistsException("User with given username already exists.");

        // TODO Mapstruct mapper would be nice
        MessengerUser messengerUser = new MessengerUser();
        messengerUser.setUuid(UUID.randomUUID());
        messengerUser.setUsername(registrationRequest.getUsername());
        messengerUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        messengerUserRepository.save(messengerUser);

        // TODO And again, use mapper
        return new UserRegistrationResponse(messengerUser.getUsername());
    }

    @Override public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
            MessengerUser messengerUser = messengerUserRepository.findByUsername(userLoginRequest.getUsername());
            if (messengerUser == null) {
                throw new UsernameNotFoundException("Username does not exist.");
            }
            String token = jwtTokenProvider.createToken(userLoginRequest.getUsername());
            return new UserLoginResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password.");
        }
    }
}
