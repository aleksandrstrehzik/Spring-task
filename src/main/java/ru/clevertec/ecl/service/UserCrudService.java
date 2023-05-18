package ru.clevertec.ecl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import ru.clevertec.ecl.dto.UserDto;

public interface UserCrudService {

    /**
     * Creates a user if the given name is not
     * already in the database
     *
     * @param userDto
     */
    void create(UserDto userDto);

    /** Returns the user with the given name
     * @param name
     * @return
     */
    UserDto getUser(String name);

    /**
     * Returns all users in the name of which there is a combination of
     * letters specified in the name parameter
     * @param name User part of the name
     * @param pageable
     * @return
     */
    Page<UserDto> getAllOrAllByPartOfName(String name, @PageableDefault(size = 3) Pageable pageable);
}
