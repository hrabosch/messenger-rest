package org.hrabosch.messenger.entity;

import lombok.Data;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class MessengerUser {

    @Id
    private UUID uuid;

    @Indexed
    private String username;

    private String password;

}
