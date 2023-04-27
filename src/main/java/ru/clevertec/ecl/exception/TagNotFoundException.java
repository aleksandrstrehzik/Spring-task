package ru.clevertec.ecl.exception;

import lombok.Getter;

@Getter
public class TagNotFoundException extends RuntimeException {

    private final String why;
    private final int code;

    public TagNotFoundException(String message, int code) {
        this.why = message;
        this.code = code;
    }
}
