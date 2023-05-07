package ru.clevertec.ecl.repository.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl extends BaseDAO<GiftCertificate> implements GiftCertificateDao {

    public GiftCertificateDaoImpl() {
        super(GiftCertificate.class);
    }

    @Override
    public Optional<GiftCertificate> getByName(String name, Session session) {
        return session.createQuery("select g from GiftCertificate g where g.name = :gName", GiftCertificate.class)
                .setParameter("gName", name)
                .uniqueResultOptional();
    }

    public List<GiftCertificate> getAll(int start, int total, String sort, String order, Session session) {
        return session.createNativeQuery("select * from gift_certificate g " +
                        "order by " + sort + " " + order + " limit " + total + " offset " + start, GiftCertificate.class)
                .list();
    }

    public List<GiftCertificate> getAllWithTag(int start, int total, String sort, String order, String tag, Session session) {
        return session.createNativeQuery("select * from gift_certificate g left join gift_certificate_tag gct on g.id = gct.gift_id " +
                "where tag_id = (select t.id from tag t where t.name = '" + tag + "') order by " + sort + " " + order + " limit " + total + " offset " + start, GiftCertificate.class)
                .list();
    }
}
