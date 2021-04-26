package org.hrabosch.messenger.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserInfo {

    UUID uuid;
    String username;

}
