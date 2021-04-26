package org.hrabosch.messenger.model.message;

import lombok.Data;

import java.util.UUID;

@Data
public class SendMessageRequest {
    private UUID recipient;
    private String payload;
}
