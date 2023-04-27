package ru.clevertec.ecl.service.Impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.mappers.TagMapper;
import ru.clevertec.ecl.exception.TagNotFoundException;
import ru.clevertec.ecl.repository.dao.TagDao;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.util.HibernateUtil;
import ru.clevertec.ecl.service.TagCrudService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagCrudServiceImpl implements TagCrudService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;

    @Override
    public TagDto getByName(String name) {
        try (Session session = HibernateUtil.getSession()) {
            Optional<Tag> tag = tagDao.getByName(name, session);
            if (tag.isEmpty()) {
                throw new TagNotFoundException("name = " + name, 40401);
            } return tagMapper.toDto(tag.get());
        }
    }

    @Override
    public TagDto getById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Optional<Tag> tag = tagDao.findById(id, session);
            if (tag.isEmpty()) {
                throw new TagNotFoundException("id = " + id, 40401);
            } return tagMapper.toDto(tag.get());
        }
    }

    @Override
    public void create(TagDto tagDto) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            tagDao.save(tagMapper.toEntity(tagDto), session);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String name) {
        try (Session session = HibernateUtil.getSession()) {
            session.getTransaction().begin();
            Optional<Tag> tagByName = tagDao.getByName(name, session);
            tagByName.ifPresent(tag -> tagDao.deleteTag(tag, session));
            session.getTransaction().commit();
        }
    }
}
