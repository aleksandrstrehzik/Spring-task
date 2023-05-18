package ru.clevertec.ecl.service.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(staticName = "aGift")
@With
@Getter
public class GiftCertificateTestBuilder implements TestBuilder<GiftCertificate>, TestDtoBuilder<GiftCertificateDto> {

    private Long id = 1L;
    private String name = "gift";
    private String description = "some description";
    private BigDecimal price = new BigDecimal("89.90");
    private Integer duration = 90;
    private Instant createDate = Instant.now();
    private Instant lastUpdateDate = Instant.now().plusSeconds(1000000);
    private List<Tag> tags = new ArrayList<>();
    private List<TagDto> tagDto = new ArrayList<>();

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

    @Override
    public GiftCertificateDto buildDto() {
        final GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setName(name);
        giftCertificateDto.setDescription(description);
        giftCertificateDto.setPrice(price);
        giftCertificateDto.setDuration(duration);
        giftCertificateDto.setCreateDate(createDate);
        giftCertificateDto.setTags(tagDto);
        return giftCertificateDto;
    }
}
