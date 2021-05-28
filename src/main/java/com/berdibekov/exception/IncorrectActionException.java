package com.berdibekov.exception;

public class IncorrectActionException extends RuntimeException {
    public IncorrectActionException() {
        super();
    }

    public IncorrectActionException(String message) {
        super(message);
    }

    public IncorrectActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
