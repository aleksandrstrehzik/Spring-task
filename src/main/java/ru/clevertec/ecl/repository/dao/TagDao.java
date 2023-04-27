package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Optional;

public interface TagDao extends DAO<Tag> {

    Optional<Tag> getByName(String name, Session session);

    void deleteTag(Tag tag, Session session);
}
