package ru.clevertec.ecl.exception;

public class GiftCertificateNotFoundException extends EntityNotFoundException {

    public GiftCertificateNotFoundException(String message, int code) {
        super(message, code);
    }
}
