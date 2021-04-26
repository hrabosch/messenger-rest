package org.hrabosch.messenger.exception;

import java.util.UUID;

public class MessageNotFoundException extends RuntimeException{

    public MessageNotFoundException(UUID uuid) {
    }
}
