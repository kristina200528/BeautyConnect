package ru.itis.exception;

public class BeautyServiceNotFoundException extends RuntimeException{
    public BeautyServiceNotFoundException(String message) {
        super(message);
    }

}
