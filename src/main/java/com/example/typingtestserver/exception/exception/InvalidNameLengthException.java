package com.example.typingtestserver.exception.exception;

public class InvalidNameLengthException extends RuntimeException {
    public InvalidNameLengthException(String message) {
        super(message);
    }
}