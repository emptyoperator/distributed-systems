package org.leniv.distributed.systems.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {
    public UserNotFoundException(long id) {
        super(HttpStatus.NOT_FOUND, "User with id '%s' not found".formatted(id));
    }
}
