package com.leboro.util.exception;

public class JSONFormatException extends RuntimeException {

    public JSONFormatException() {
        super();
    }

    public JSONFormatException(String message) {
        super(message);
    }

    public JSONFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONFormatException(Throwable cause) {
        super(cause);
    }
}