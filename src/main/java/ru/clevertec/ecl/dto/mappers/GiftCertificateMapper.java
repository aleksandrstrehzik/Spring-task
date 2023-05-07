package ru.clevertec.ecl.dto.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

    GiftCertificate toEntity(GiftCertificateDto dto);

    GiftCertificateDto toDto(GiftCertificate entity);
}
