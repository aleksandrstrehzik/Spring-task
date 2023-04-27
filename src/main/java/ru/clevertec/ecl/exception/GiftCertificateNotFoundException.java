package ru.clevertec.ecl.exception;

import lombok.Getter;

@Getter
public class GiftCertificateNotFoundException extends RuntimeException {

   private final String why;
   private final int code;

    public GiftCertificateNotFoundException(String message, int code) {
        this.why = message;
        this.code = code;
    }
}
