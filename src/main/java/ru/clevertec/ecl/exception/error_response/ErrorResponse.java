package ru.clevertec.ecl.exception.error_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String description;
    private int code;

}
