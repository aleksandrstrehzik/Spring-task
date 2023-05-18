package ru.clevertec.ecl.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto {

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 20, message = "message should be between 2 to 20")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Price cannot be empty")
    @Min(value = 0, message = "Price should be greater then 0")
    private BigDecimal price;

    @NotNull(message = "Duration cannot be empty")
    private Integer duration;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant createDate;
    private List<TagDto> tags;
}
