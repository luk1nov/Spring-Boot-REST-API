package com.epam.esm.exceptions;

public class InvalidDataProvidedException extends RuntimeException{
    private final int ERROR_CODE = 50001;

    public InvalidDataProvidedException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
