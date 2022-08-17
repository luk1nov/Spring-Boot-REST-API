package com.epam.esm.exceptions;

public class EntityCreationException extends RuntimeException{
    private final int ERROR_CODE = 50004;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
