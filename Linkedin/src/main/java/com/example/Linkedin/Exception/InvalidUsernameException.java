package com.example.Linkedin.Exception;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String message) {
        super(message);
    }

    public InvalidUsernameException() {
        super("This username is already taken");
    }
}
