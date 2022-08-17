package com.epam.esm.exception;

import com.epam.esm.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
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

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException e, Locale locale){
        return exceptionResponseBuilder(e, e.getErrorCode(), locale);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ExceptionResponse handleEntityAlreadyExists(EntityAlreadyExistsException e, Locale locale){
        return exceptionResponseBuilder(e, e.getErrorCode(), locale);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleUpdatingEntityException(InvalidDataProvidedException e, Locale locale){
        ExceptionResponse response = exceptionResponseBuilder(e, e.getErrorCode(), locale);
        response.setDescription(e.getMessage());
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleEntityCreationException(EntityCreationException e, Locale locale){
        return exceptionResponseBuilder(e, e.getErrorCode(), locale);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleEntityCreationException(EntityModifyingException e, Locale locale){
        return exceptionResponseBuilder(e, e.getErrorCode(), locale);
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleInternalServerException(InternalServerException e, Locale locale){
        return exceptionResponseBuilder(e, e.getErrorCode(), locale);
    }

    private ExceptionResponse exceptionResponseBuilder(Throwable e, int errorCode, Locale locale){
        return ExceptionResponse.builder()
                .errorCode(errorCode)
                .message(getErrorMessageFromResourceBundle(errorCode, locale))
                .description(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    private String getErrorMessageFromResourceBundle(int errorCode, Locale locale){
        String key = "error." + errorCode;
        return resourceBundleMessageSource.getMessage(key, null, locale);
    }
}
