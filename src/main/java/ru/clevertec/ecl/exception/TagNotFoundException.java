package ru.clevertec.ecl.exception;

public class TagNotFoundException extends EntityNotFoundException {

    public TagNotFoundException(String message, int code) {
        super(message, code);
    }
}
