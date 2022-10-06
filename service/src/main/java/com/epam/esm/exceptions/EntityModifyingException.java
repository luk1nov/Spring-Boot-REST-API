package com.epam.esm.exceptions;

public class EntityModifyingException extends RuntimeException{

    public EntityModifyingException() {
    }

    public EntityModifyingException(String message) {
        super(message);
    }

    private final int ERROR_CODE = 40003;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
