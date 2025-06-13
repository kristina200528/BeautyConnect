package ru.itis.exception;

public class ServiceOfferingNotFoundException extends RuntimeException {

    public ServiceOfferingNotFoundException(String message) {
        super(message);
    }

}
