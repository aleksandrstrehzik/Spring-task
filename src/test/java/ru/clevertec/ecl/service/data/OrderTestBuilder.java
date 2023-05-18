package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.User;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aOrder")
@With
@Getter
public class OrderTestBuilder implements TestBuilder<Order>, TestDtoBuilder<OrderDto> {

    private Long id = 1L;
    private Instant purchase = Instant.now();
    private BigDecimal price = BigDecimal.valueOf(1209, 2);
    private User user = null;
    private GiftCertificate giftCertificate = null;
    private GiftCertificateDto giftCertificateDto = null;

    @Override
    public Order build() {
        return Order.builder()
                .id(id)
                .price(price)
                .purchase(purchase)
                .user(user)
                .giftCertificate(giftCertificate)
                .build();
    }

    @Override
    public OrderDto buildDto() {
        return OrderDto.builder()
                .price(price)
                .purchase(purchase)
                .giftCertificate(null)
                .build();
    }
}
