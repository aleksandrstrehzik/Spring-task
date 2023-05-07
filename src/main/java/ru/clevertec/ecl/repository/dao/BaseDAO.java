package ru.clevertec.ecl.repository.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class BaseDAO<E> implements DAO<E> {

    private final Class<E> clazz;

    @Override
    public Optional<E> findById(Long id, Session session) {
        return Optional.ofNullable(session.get(clazz, id));
    }

    @Override
    public void delete(E entity, Session session) {
        session.remove(entity);
    }

    @Override
    public E update(E entity, Session session) {
        return session.merge(entity);
    }

    @Override
    public void save(E entity, Session session) {
        session.persist(entity);
    }
}
