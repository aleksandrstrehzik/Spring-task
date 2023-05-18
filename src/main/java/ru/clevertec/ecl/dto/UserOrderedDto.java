package ru.clevertec.ecl.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserOrderedDto {

    private String userName;
    private String giftName;
}
