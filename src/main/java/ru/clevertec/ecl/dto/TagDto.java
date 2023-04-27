package ru.clevertec.ecl.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDto {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 20, message = "message should be between 2 to 20")
    private String name;
}
