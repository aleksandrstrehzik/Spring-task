package ru.clevertec.ecl.dto.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.repository.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagDto dto);

    TagDto toDto(Tag entity);
}
