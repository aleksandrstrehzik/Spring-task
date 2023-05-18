package ru.clevertec.ecl.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.mappers.UserMapper;
import ru.clevertec.ecl.exception.UserNotFoundException;
import ru.clevertec.ecl.repository.dao.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.data.UserTestBuilder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserCrudServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserCrudServiceImpl userCrudService;

    @Test
    void create() {
        UserDto userDto = UserTestBuilder.aUser().buildDto();
        User user = UserTestBuilder.aUser().build();

        Mockito.doReturn(user)
                .when(userMapper).toEntity(userDto);

        userCrudService.create(userDto);

        Mockito.verify(userRepository).save(user);
    }

    @Test
    void getUser() {
        UserDto userDto = UserTestBuilder.aUser().buildDto();
        User user = UserTestBuilder.aUser().build();
        String name = "name";

        Mockito.doReturn(Optional.of(user))
                .when(userRepository).findByName(name);

        Mockito.doReturn(userDto)
                .when(userMapper).toDto(user);

        assertThat(userCrudService.getUser(name)).isEqualTo(userMapper.toDto(user));
    }

    @Test
    void checkGetNameThrow() {
        Mockito.doReturn(Optional.empty())
                .when(userRepository).findByName("name");

        assertThrows(UserNotFoundException.class,
                () -> userCrudService.getUser("name"));
    }

    @Test
    void getAllOrAllByPartOfName() {
        String some_name = "some name";
        PageRequest pageRequest = PageRequest.of(5, 5);
        User first = UserTestBuilder.aUser().withName("first").build();
        User second = UserTestBuilder.aUser().withName("second").build();
        User third = UserTestBuilder.aUser().withName("third").build();
        List<User> list = List.of(first, second, third);
        Page<User> actual = new PageImpl<>(list);

        Mockito.doReturn(actual)
                .when(userRepository).findAllOrAllByPartName(some_name, pageRequest);

        Page<UserDto> expected = userCrudService.getAllOrAllByPartOfName(some_name, pageRequest);
        assertThat(expected.getTotalElements()).isEqualTo(actual.getTotalElements());
    }
}