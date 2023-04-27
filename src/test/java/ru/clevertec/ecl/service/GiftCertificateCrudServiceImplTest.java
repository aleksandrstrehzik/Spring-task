package ru.clevertec.ecl.service;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.mappers.GiftCertificateMapper;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.repository.dao.GiftCertificateDao;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.data.GiftCertificateTestBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateCrudServiceImplTest {

    @Spy
    private GiftCertificateDao giftDao;
    @Spy
    private GiftCertificateMapper giftMapper;
    @InjectMocks
    private GiftCertificateCrudService giftCrudService;

    @Test
    void checkGetByName() {
        try (Session session = HibernateUtil.getSession()) {
            GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
            String name = gift.getName();

            Mockito.doReturn(Optional.of(gift))
                    .when(giftDao).getByName(name, session);

            assertThat(giftCrudService.getByName(name)).isEqualTo(giftMapper.toDto(gift));
        }
    }

    @Test
    void checkGetNameThrow() {
        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.getByName("sd"));
    }

    @Test
    void checkGetById() {
        try (Session session = HibernateUtil.getSession()) {
            GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
            Long id = gift.getId();

            Mockito.doReturn(Optional.of(gift))
                    .when(giftDao).findById(id, session);

            assertThat(giftCrudService.getById(id)).isEqualTo(giftMapper.toDto(gift));
        }
    }

    @Test
    void checkGetByIdThrow() {
        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.getById(100L));
    }

    @Test
    void checkDelete() {
        try (Session session = HibernateUtil.getSession()) {
            GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
            String name = gift.getName();

            Mockito.doReturn(Optional.of(gift))
                    .when(giftDao).getByName(name, session);

            Mockito.verify(giftDao).delete(gift, session);
        }
    }

    @Test
    void checkDeleteShouldThrow() {
        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.delete("sd"));
    }

    @Test
    void checkCreate() {
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
        giftCrudService.create(giftMapper.toDto(gift));
        GiftCertificateDto giftFromDB = giftCrudService.getByName(gift.getName());

        assertThat(gift.getName()).isEqualTo(giftFromDB.getName());
        assertThat(gift.getDescription()).isEqualTo(giftFromDB.getDescription());
        assertThat(gift.getDuration()).isEqualTo(giftFromDB.getDuration());
        assertThat(gift.getPrice()).isEqualTo(giftFromDB.getPrice());
    }

    @Test
    void update() {
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
        giftCrudService.create(giftMapper.toDto(gift));
        gift.setName("name1");
        gift.setPrice(new BigDecimal("90.99"));
        gift.setDuration(7);
        gift.setDescription("another description");
        giftCrudService.update(giftMapper.toDto(gift));

        GiftCertificateDto giftFromDB = giftCrudService.getById(gift.getId());

        assertThat(gift.getName()).isNotEqualTo(giftFromDB.getName());
        assertThat(gift.getDescription()).isNotEqualTo(giftFromDB.getDescription());
        assertThat(gift.getDuration()).isNotEqualTo(giftFromDB.getDuration());
        assertThat(gift.getPrice()).isNotEqualTo(giftFromDB.getPrice());
        assertThat(gift.getCreateDate()).isEqualTo(giftFromDB.getCreateDate());
    }

}