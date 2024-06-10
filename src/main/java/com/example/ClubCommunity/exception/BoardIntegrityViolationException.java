package com.example.ClubCommunity.exception;

public class BoardIntegrityViolationException extends RuntimeException {
    public BoardIntegrityViolationException(String message) {
        super(message);
    }
}