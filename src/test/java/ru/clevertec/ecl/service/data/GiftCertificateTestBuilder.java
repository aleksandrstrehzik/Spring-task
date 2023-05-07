package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aGift")
@With
@Getter
public class GiftCertificateTestBuilder implements TestBuilder<GiftCertificate>{

    private Long id = 1L;
    private String name = "gift";
    private String description = "some description";
    private BigDecimal price = new BigDecimal("89.90");
    private Integer duration = 90;
    private ZonedDateTime createDate = ZonedDateTime.now();
    private ZonedDateTime lastUpdateDate = ZonedDateTime.now().plusDays(9L);
    private List<Tag> tags = new ArrayList<>();

    @Override
    public GiftCertificate build() {
        final GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(description);
        giftCertificate.setPrice(price);
        giftCertificate.setDuration(duration);
        giftCertificate.setCreateDate(createDate);
        giftCertificate.setLastUpdateDate(lastUpdateDate);
        giftCertificate.setTags(tags);
        return giftCertificate;
    }
}
