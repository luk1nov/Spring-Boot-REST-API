package com.epam.esm.exception;

import com.epam.esm.exceptions.EntityNotFoundException;
import com.epam.esm.exceptions.InternalServerException;
import com.epam.esm.exceptions.InvalidDataProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ControllerAdvisor {
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public ControllerAdvisor(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException e, Locale locale){
        return exceptionResponseBuilder(e.getErrorCode(), locale);

    }

    @ExceptionHandler(InvalidDataProvidedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleInvalidDataProvidedException(InvalidDataProvidedException e, Locale locale){
        return exceptionResponseBuilder(e.getErrorCode(), locale);

    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerException(InvalidDataProvidedException e, Locale locale){
        return exceptionResponseBuilder(e.getErrorCode(), locale);
    }

    private ExceptionResponse exceptionResponseBuilder(int errorCode, Locale locale){
        return ExceptionResponse.builder()
                .errorCode(errorCode)
                .message(getErrorMessageFromResourceBundle(errorCode, locale))
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String getErrorMessageFromResourceBundle(int errorCode, Locale locale){
        String key = "error." + errorCode;
        return resourceBundleMessageSource.getMessage(key, null, locale);
    }
}
