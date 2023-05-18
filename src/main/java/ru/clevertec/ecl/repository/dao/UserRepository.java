package ru.clevertec.ecl.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.repository.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    @Query("select u from User u where ?1 is null or u.name like concat('%', ?1, '%')")
    Page<User> findAllOrAllByPartName(String name, Pageable pageable);
}
