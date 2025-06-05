package com.example.typingtestserver.exception;

public class ProfanityException extends RuntimeException {
    public ProfanityException(String message) {
        super(message);
    }
}