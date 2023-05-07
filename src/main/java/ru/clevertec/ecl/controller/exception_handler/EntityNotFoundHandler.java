package ru.clevertec.ecl.controller.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.exception.TagNotFoundException;
import ru.clevertec.ecl.exception.error_response.ErrorResponse;

@ControllerAdvice
public class EntityNotFoundHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGiftNotFoundException(GiftCertificateNotFoundException giftNotFound) {
        ErrorResponse response = ErrorResponse.builder()
                .description("There is no gift certificate with" + giftNotFound.getWhy())
                .code(giftNotFound.getCode())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTagNotFoundException(TagNotFoundException tagNotFound) {
        ErrorResponse response = ErrorResponse.builder()
                .description("There is no gift certificate with" + tagNotFound.getWhy())
                .code(tagNotFound.getCode())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
