package org.hrabosch.messenger.model.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotEmpty
    String username;

    @NotEmpty
    String password;

}
