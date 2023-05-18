package ru.clevertec.ecl.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query(value = "select * " +
            "from tag " +
            "where tag.id = (select gct.tag_id " +
            "from (select o.user_id from orders o group by o.user_id order by sum(o.price) DESC limit 1) u " +
            "left join orders o on o.user_id = u.user_id " +
            "left join gift_certificate_tag gct on o.gift_id = gct.gift_id " +
            "group by gct.tag_id " +
            "order by count(gct.tag_id) DESC " +
            "limit 1)", nativeQuery = true)
    Optional<Tag> findMostFrequencyUsedByMostActiveUser();
}
