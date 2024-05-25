package com.bank.Bank.System.exceptions;



import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> notFoundException(ChangeSetPersister.NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // message can be added to body 
    }
}
