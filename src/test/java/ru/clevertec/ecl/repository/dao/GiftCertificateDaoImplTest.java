package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.data.GiftCertificateTestBuilder;
import ru.clevertec.ecl.service.data.TagTestBuilder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftDao = new GiftCertificateDaoImpl();
   /*
    @BeforeEach
    void setUp() {
        try (Session session = HibernateUtil.getSession()) {
            GiftCertificate svidler1 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler1").withId(1L).build();
            GiftCertificate gift2 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler2").withId(2L).build();
            GiftCertificate gift3 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler3").withId(3L).build();
            GiftCertificate gift4 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler4").withId(4L).build();
            giftDao.save(gift1, session);
            giftDao.save(gift2, session);
            giftDao.save(gift3, session);
            giftDao.save(gift4, session);
        }
    }*/

    @Test
    void checkGetByName() {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            GiftCertificate svidler1 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler1").withId(1L).build();
            session.save(svidler1);
            session.getTransaction().commit();

            GiftCertificate gift = giftDao.getByName("Svidler1", session).get();

            assertThat(gift).isEqualTo(svidler1);
        }
    }

    @Test
    void checkGetAll() {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            GiftCertificate svidler1 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler1").withId(1L).build();
            GiftCertificate svidler2 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler2").withId(2L).build();
            GiftCertificate svidler3 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler3").withId(3L).build();
            session.save(svidler1);
            session.save(svidler2);
            session.save(svidler3);
            session.getTransaction().commit();

            int allGiftNumber = giftDao.getAll(0, 10, "g.name", "ASC", session).size();


            assertThat(allGiftNumber).isEqualTo(3);
        }
    }

    @Test
    void getAllWithTag() {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            Tag tag = TagTestBuilder.aTag().withId(1L).withName("tagName").build();
            session.save(tag);
            GiftCertificate svidler1 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler1").withId(1L).build();
            GiftCertificate svidler2 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler2").withId(2L).build();
            GiftCertificate svidler3 = GiftCertificateTestBuilder.aGift()
                    .withName("Svidler3").withId(3L).withTags(List.of(tag)).build();
            session.save(svidler1);
            session.save(svidler2);
            session.save(svidler3);
            session.getTransaction().commit();

            int allGiftNumber = giftDao.getAllWithTag(0, 10, "g.name", "ASC", "tagName", session).size();


            assertThat(allGiftNumber).isEqualTo(1);
        }
    }
}