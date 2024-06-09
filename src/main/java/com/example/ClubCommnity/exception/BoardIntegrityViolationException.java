package com.example.ClubCommnity.exception;

public class BoardIntegrityViolationException extends RuntimeException {
    public BoardIntegrityViolationException(String message) {
        super(message);
    }
}