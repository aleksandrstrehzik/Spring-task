package ru.clevertec.ecl.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.repository.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    Optional<GiftCertificate> findByName(String name);

    @Query("select g from GiftCertificate g where ?1 is null or g.name like concat('%', ?1, '%')")
    Page<GiftCertificate> findAllOrAllByPartName(String name, Pageable pageable);
}
