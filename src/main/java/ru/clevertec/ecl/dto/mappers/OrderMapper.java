package ru.clevertec.ecl.dto.mappers;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.repository.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto dto);

    OrderDto toDto(Order entity);
}
