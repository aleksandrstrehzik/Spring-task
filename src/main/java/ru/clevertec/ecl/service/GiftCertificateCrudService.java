package ru.clevertec.ecl.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;

public interface GiftCertificateCrudService {

    /**
     * The method returns an object by the given name from the database and throws exception
     * if there is no object with this name
     * @param name
     * @return GiftCertificateDto
     */
    GiftCertificateDto getByName(String name);

    /**
     * The method returns an object by the given id from the database and throws exception
     * if there is no object with this id
     * @param id
     * @return GiftCertificateDto
     */
    GiftCertificateDto getById(Long id);


    /**
     * Removes an object from the database by the given name
     * @param name
     */
    @Transactional
    void delete(String name);

    /**
     * Saves the object to the database
     * @param giftDto
     */
    @Transactional
    void create(GiftCertificateDto giftDto);

    /**
     * Updates the specified fields of the object in the database
     * @param giftDto
     * @return
     */
    @Transactional
    GiftCertificateDto update(GiftCertificateDto giftDto);

    /**
     * Returns all certificates in the name of which there is a combination of
     * letters specified in the name parameter
     *
     * @param name     Gift certificate part of the name
     * @param pageable
     * @return
     */
    Page<GiftCertificateDto> getAllOrAllByPartOfName(String name, Pageable pageable);
}
