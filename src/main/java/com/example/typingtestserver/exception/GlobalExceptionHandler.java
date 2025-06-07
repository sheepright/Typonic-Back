package com.example.typingtestserver.exception;

import com.example.typingtestserver.exception.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEmailFormat(InvalidEmailFormatException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(1001, ex.getMessage()));
    }

    @ExceptionHandler(ProfanityException.class)
    public ResponseEntity<ErrorResponse> handleProfanity(ProfanityException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(1002, ex.getMessage()));
    }

    @ExceptionHandler(InvalidNameLengthException.class)
    public ResponseEntity<ErrorResponse> handleInvalidNameLength(InvalidNameLengthException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(1003, ex.getMessage()));
    }

    @ExceptionHandler(InvalidRankingValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRankingValue(InvalidRankingValueException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(1004, ex.getMessage()));
    }
}