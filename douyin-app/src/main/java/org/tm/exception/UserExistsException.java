package org.tm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FOUND)
public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
