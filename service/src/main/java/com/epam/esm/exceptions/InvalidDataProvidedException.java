package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataProvidedException extends RuntimeException{
    private final int ERROR_CODE = 40001;

    public InvalidDataProvidedException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
