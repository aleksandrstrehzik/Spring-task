package ru.clevertec.ecl.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException(String message, int code) {
        super(message, code);
    }
}
