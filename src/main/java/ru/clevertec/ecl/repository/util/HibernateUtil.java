package ru.clevertec.ecl.repository.util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

@UtilityClass
public class HibernateUtil {

    public static Session getSession() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(GiftCertificate.class);
        configuration.addAnnotatedClass(Tag.class);
        configuration.configure();
        return configuration.buildSessionFactory().openSession();
    }
}
