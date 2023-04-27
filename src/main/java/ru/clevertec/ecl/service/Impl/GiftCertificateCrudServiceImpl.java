package ru.clevertec.ecl.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.mappers.GiftCertificateMapper;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.repository.dao.GiftCertificateDao;
import ru.clevertec.ecl.repository.dao.TagDao;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.GiftCertificateCrudService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class GiftCertificateCrudServiceImpl implements GiftCertificateCrudService {

    private final GiftCertificateDao giftDao;
    private final GiftCertificateMapper giftMapper;
    private final TagDao tagDao;

    @Override
    public GiftCertificateDto getByName(String name) {
        try (Session session = HibernateUtil.getSession()) {
            Optional<GiftCertificate> giftFromDB = giftDao.getByName(name, session);
            if (giftFromDB.isPresent()) {
                return giftMapper.toDto(giftFromDB.get());
            } else throw new GiftCertificateNotFoundException("name = " + name, 40402);
        }
    }

    @Override
    public GiftCertificateDto getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Optional<GiftCertificate> giftCertificate = giftDao.findById(id, session);
            if (giftCertificate.isPresent()) {
                return giftMapper.toDto(giftCertificate.get());
            } else throw new GiftCertificateNotFoundException("id = " + id, 40401);
        }
    }

    @Override
    @Transactional
    public void delete(String name) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            Optional<GiftCertificate> giftByName = giftDao.getByName(name, session);
            if (giftByName.isPresent()) {
                giftDao.delete(giftByName.get(), session);
            } else throw new GiftCertificateNotFoundException("name = " + name, 40401);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public void create(GiftCertificateDto giftDto) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            GiftCertificate gift = giftMapper.toEntity(giftDto);
            ZonedDateTime now = ZonedDateTime.now();
            gift.setCreateDate(now);
            gift.setLastUpdateDate(now);
            gift.setTags(setTagsToGiftCertificate(session, gift));
            giftDao.save(gift, session);
            session.getTransaction().commit();
        }
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftDto) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            Optional<GiftCertificate> giftFromDBOpt = giftDao.getByName(giftDto.getName(), session);
            if (giftFromDBOpt.isEmpty()) {
                throw new GiftCertificateNotFoundException("name = " + giftDto.getName(), 40402);
            }
            GiftCertificate giftFromDB = giftFromDBOpt.get();
            giftFromDB.setLastUpdateDate(ZonedDateTime.now());
            if (giftDto.getDescription() != null) giftFromDB.setDescription(giftDto.getDescription());
            if (giftDto.getPrice() != null) giftFromDB.setPrice(giftDto.getPrice());
            if (giftDto.getDuration() != null) giftFromDB.setDuration(giftDto.getDuration());
            if (giftDto.getTags() != null) {
                List<Tag> tags = setTagsToGiftCertificate(session, giftMapper.toEntity(giftDto));
                giftFromDB.setTags(tags);
            }
            session.getTransaction().commit();
            return giftMapper.toDto(giftFromDB);
        }
    }


    private List<Tag> setTagsToGiftCertificate(Session session, GiftCertificate gift) {
        if (!gift.getTags().isEmpty()) {
            List<Tag> tags = gift.getTags();
            tags.stream()
                    .filter(name -> !IsTagPresented(name.getName(), session))
                    .forEach(createTag -> tagDao.save(createTag, session));
            List<Tag> allTags = tags.stream()
                    .map(tagOpt -> tagDao.getByName(tagOpt.getName(), session))
                    .map(Optional::get)
                    .collect(toList());
            return allTags;
        } else return null;
    }

    private boolean IsTagPresented(String tag, Session session) {
        return tagDao.getByName(tag, session).isPresent();
    }
}
