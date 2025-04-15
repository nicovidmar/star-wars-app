package com.starwars.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.starwars.constants.Constants;
import com.starwars.exception.CustomizableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomizableException.class)
    public ResponseEntity<String> handleUnauthorizedException(CustomizableException ex) {
        LOGGER.error(Constants.CUSTOM_EXCEPTION_LOG, ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        LOGGER.error(Constants.GENERAL_EXCEPTION_LOG, ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Constants.UNEXPECTED_ERROR_MESSAGE + ex.getMessage());
    }
}
