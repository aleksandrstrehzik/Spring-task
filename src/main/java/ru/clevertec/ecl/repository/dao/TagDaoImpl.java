package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Optional;

@Repository
public class TagDaoImpl extends BaseDAO<Tag> implements TagDao {

    public TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public Optional<Tag> getByName(String name, Session session) {
        return session.createQuery("select t from Tag t where name = :tName", Tag.class)
                .setParameter("tName", name)
                .uniqueResultOptional();
    }

    public void deleteTag(Tag tag, Session session) {
        session.createNativeQuery("delete from gift_certificate_tag where tag_id = :id", Tag.class)
                .setParameter("id", tag.getId())
                .executeUpdate();
        super.delete(tag, session);
    }
}
