package org.hrabosch.messenger.entity;

import lombok.Data;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class UserMessage {
    @Id
    private UUID uuid;

    @Indexed
    private UUID sender;

    @Indexed
    private UUID recipient;

    private String content;

}
