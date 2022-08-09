package com.epam.esm.exception;

import com.epam.esm.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvisor {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public ControllerAdvisor(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e, Locale locale){
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.NOT_FOUND);
    }


    private Map<String, Object> createResponse(int errorCode, Locale locale){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(ERROR_CODE, errorCode);
        response.put(ERROR_MESSAGE, resourceBundleMessageSource.getMessage(getMessageByCode(errorCode), null, locale));
        return response;
    }


    private String getMessageByCode(int errorCode) {
        return "error." + errorCode;
    }
}
