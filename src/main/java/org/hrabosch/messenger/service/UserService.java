package org.hrabosch.messenger.service;

import org.hrabosch.messenger.model.login.UserLoginRequest;
import org.hrabosch.messenger.model.login.UserLoginResponse;
import org.hrabosch.messenger.model.registration.UserRegistrationRequest;
import org.hrabosch.messenger.model.registration.UserRegistrationResponse;

public interface UserService {

    UserRegistrationResponse registerUser(UserRegistrationRequest registrationRequest);

    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);


}
