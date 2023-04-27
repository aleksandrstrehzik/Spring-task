package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends DAO<GiftCertificate>{

    Optional<GiftCertificate> getByName(String name, Session session);

    List<GiftCertificate> getAll(int start, int total, String sort,String order, Session session);

    List<GiftCertificate> getAllWithTag(int start, int total, String sort, String order, String tag, Session session);
}
