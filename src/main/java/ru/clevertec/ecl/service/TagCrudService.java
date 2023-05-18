package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.TagDto;

public interface TagCrudService {

    /**
     * the method returns an object by the given name from the database and throws exception
     * if there is no object with this name
     *
     * @param name
     * @return
     */
    TagDto getByName(String name);

    /**
     * The method returns an object by the given id from the database and throws exception
     * if there is no object with this id
     *
     * @param id
     * @return
     */
    TagDto getById(Long id);

    /**
     * Saves the object to the database
     *
     * @param tagDto
     */
    void create(TagDto tagDto);

    /**
     * Removes an object from the database by the given name
     *
     * @param name
     */
    void delete(String name);
}
