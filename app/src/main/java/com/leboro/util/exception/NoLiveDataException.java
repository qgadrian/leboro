package com.leboro.util.exception;

public class NoLiveDataException extends Exception {

    public NoLiveDataException() {
        super("No live data found");
    }

    public NoLiveDataException(String message) {
        super(message);
    }

}
