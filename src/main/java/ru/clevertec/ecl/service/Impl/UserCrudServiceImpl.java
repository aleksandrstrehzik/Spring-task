package ru.clevertec.ecl.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.dto.mappers.UserMapper;
import ru.clevertec.ecl.exception.UserNotFoundException;
import ru.clevertec.ecl.repository.dao.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.UserCrudService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCrudServiceImpl implements UserCrudService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(UserDto userDto) {
        userRepository.save(userMapper.toEntity(userDto));
    }

    @Override
    public UserDto getUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
        } else throw new UserNotFoundException("name = " + name, 40402);
    }

    @Override
    public Page<UserDto> getAllOrAllByPartOfName(String name, @PageableDefault(size = 3) Pageable pageable) {
        return userRepository.findAllOrAllByPartName(name, pageable).map(userMapper::toDto);
    }
}
