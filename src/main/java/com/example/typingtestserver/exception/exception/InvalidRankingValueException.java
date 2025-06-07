package com.example.typingtestserver.exception.exception;

public class InvalidRankingValueException extends RuntimeException {
    public InvalidRankingValueException(String message) {
        super(message);
    }
}