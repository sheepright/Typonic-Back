package com.example.typingtestserver.exception;

public class InvalidNameLengthException extends RuntimeException {
    public InvalidNameLengthException(String message) {
        super(message);
    }
}