package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.User;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aUser")
@With
@Getter
public class UserTestBuilder implements TestBuilder<User>, TestDtoBuilder<UserDto> {

    private Long id = 1L;
    private String name = "user";
    private List<Order> orders = new ArrayList<>();

    @Override
    public User build() {
        return User.builder()
                .id(id)
                .name(name)
                .orders(orders)
                .build();
    }

    @Override
    public UserDto buildDto() {
        return UserDto.builder()
                .name(name)
                .build();
    }
}
