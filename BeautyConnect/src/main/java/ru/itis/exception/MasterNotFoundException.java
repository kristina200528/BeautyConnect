package ru.itis.exception;

public class MasterNotFoundException extends RuntimeException {

    public MasterNotFoundException(String message) {
        super(message);
    }

}
