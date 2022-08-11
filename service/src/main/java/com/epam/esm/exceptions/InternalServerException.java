package com.epam.esm.exceptions;

public class InternalServerException extends RuntimeException{
    private final int ERROR_CODE = 50002;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
