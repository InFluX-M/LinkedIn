package com.example.Linkedin.Exception;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
    public InvalidEmailException() {
        super("Email format is invalid");
    }
}
