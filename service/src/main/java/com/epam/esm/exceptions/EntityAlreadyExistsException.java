package com.epam.esm.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{
    private final int ERROR_CODE = 50002;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
