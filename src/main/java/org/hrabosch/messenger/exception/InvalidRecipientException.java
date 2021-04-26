package org.hrabosch.messenger.exception;

import java.util.UUID;

public class InvalidRecipientException extends RuntimeException {

    public InvalidRecipientException(UUID recipient) {

    }
}
