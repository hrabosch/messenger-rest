package org.hrabosch.messenger.exception;

import java.util.UUID;

public class UnauthorizedOperation extends RuntimeException {

    public UnauthorizedOperation(UUID messageUuid, String username) {

    }
}
