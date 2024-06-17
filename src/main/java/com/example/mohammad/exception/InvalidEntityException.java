package com.example.mohammad.exception;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException(String message){
        super(message);
    }
}
