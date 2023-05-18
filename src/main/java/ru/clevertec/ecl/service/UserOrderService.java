package ru.clevertec.ecl.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserOrderedDto;

public interface UserOrderService {

    /**
     * Creates an order if the username and certificate name are in the database
     *
     * @param userOrderedDto
     */
    @Transactional
    void createOrder(UserOrderedDto userOrderedDto);

    /**
     * Returns all orders of the given user
     *
     * @param userName Gift certificate part of the name
     * @param pageable
     * @return
     */
    Page<OrderDto> getUserOrders(String userName, @PageableDefault(size = 3) Pageable pageable);

    /**
     * Returns the most frequently occurring tag in the user's orders
     * with the highest spending on certificates
     *
     * @return
     */
    TagDto findMostFrequencyUsedByMostActiveUser();
}
