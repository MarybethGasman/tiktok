package org.tm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParameterInvalidException extends RuntimeException{
    public ParameterInvalidException(String message) {
        super(message);
    }
}
