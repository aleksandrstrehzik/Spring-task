package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;

import java.util.Optional;

public interface DAO<E> {

    Optional<E> findById(Long id, Session session);

    void delete(E entity, Session session);

    E update(E entity, Session session);

    void save(E entity, Session session);
}
