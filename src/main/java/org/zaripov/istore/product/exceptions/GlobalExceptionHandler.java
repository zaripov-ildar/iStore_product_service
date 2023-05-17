package org.zaripov.istore.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppException> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new AppException("Resource not found", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
