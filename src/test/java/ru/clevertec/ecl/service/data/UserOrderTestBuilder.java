package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.UserOrderedDto;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aUserOrder")
@With
@Getter
public class UserOrderTestBuilder implements TestDtoBuilder<UserOrderedDto> {

    private String userName = "user";
    private String giftName = "gift";

    @Override
    public UserOrderedDto buildDto() {
        return UserOrderedDto.builder()
                .userName(userName)
                .giftName(giftName)
                .build();
    }
}
