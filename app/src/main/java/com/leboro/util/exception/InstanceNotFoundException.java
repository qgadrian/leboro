package com.leboro.util.exception;

public class InstanceNotFoundException extends Exception {

    private final int id;

    public InstanceNotFoundException(int id) {
        super("No instance found for id[" + id + "]");
        this.id = id;
    }

    public InstanceNotFoundException(int id, String message) {
        super(message);
        this.id = id;
    }
}
