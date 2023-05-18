package ru.clevertec.ecl.repository.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUserIs(User user, Pageable pageable);
}
