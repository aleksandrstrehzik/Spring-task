package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateFindAllService {
    List<GiftCertificateDto> getAll(String sort, String order, String page, String tag);
}
