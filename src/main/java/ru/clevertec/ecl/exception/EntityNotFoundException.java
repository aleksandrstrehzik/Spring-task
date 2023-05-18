package ru.clevertec.ecl.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private final String why;
    private final int code;

    public EntityNotFoundException(String message, int code) {
        this.why = message;
        this.code = code;
    }
}
