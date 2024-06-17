package com.example.mohammad.exception;

public class NotValidPasswordException extends RuntimeException {
    public NotValidPasswordException(String message) {
    super(message);
    }
}
