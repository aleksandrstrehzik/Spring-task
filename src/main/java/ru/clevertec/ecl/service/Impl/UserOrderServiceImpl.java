package ru.clevertec.ecl.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserOrderedDto;
import ru.clevertec.ecl.dto.mappers.OrderMapper;
import ru.clevertec.ecl.dto.mappers.TagMapper;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.exception.UserNotFoundException;
import ru.clevertec.ecl.repository.dao.GiftCertificateRepository;
import ru.clevertec.ecl.repository.dao.OrderRepository;
import ru.clevertec.ecl.repository.dao.TagRepository;
import ru.clevertec.ecl.repository.dao.UserRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.UserOrderService;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final UserRepository userRepository;
    private final GiftCertificateRepository giftRepository;
    private final OrderRepository orderRepository;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public void createOrder(UserOrderedDto userOrderedDto) {
        String userName = userOrderedDto.getUserName();
        String giftName = userOrderedDto.getGiftName();
        Optional<User> userOpt = userRepository.findByName(userName);
        Optional<GiftCertificate> giftOpt = giftRepository.findByName(giftName);
        boolean userOptPresent = userOpt.isPresent();
        boolean giftOptPresent = giftOpt.isPresent();
        if (userOptPresent && giftOptPresent) {
            GiftCertificate gift = giftOpt.get();
            User user = userOpt.get();
            Order order = Order.builder()
                    .price(gift.getPrice())
                    .purchase(Instant.now())
                    .user(user)
                    .giftCertificate(gift)
                    .build();
            orderRepository.save(order);
            user.getOrders().add(order);
            userRepository.save(user);
        }
        if (!userOptPresent) {
            throw new UserNotFoundException("No user with this name " + userName, 40401);
        }
        if (!giftOptPresent) {
            throw new GiftCertificateNotFoundException("No user with this name " + userName, 40401);
        }
    }

    @Override
    public Page<OrderDto> getUserOrders(String userName, @PageableDefault(size = 3) Pageable pageable) {
        Optional<User> userOpt = userRepository.findByName(userName);
        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("No user with this name " + userName, 40401);
        }
        return orderRepository.findAllByUserIs(userOpt.get(), pageable).map(orderMapper::toDto);
    }

    @Override
    public TagDto findMostFrequencyUsedByMostActiveUser() {
        Optional<Tag> tag = tagRepository.findMostFrequencyUsedByMostActiveUser();
        if (tag.isPresent()) {
            return tagMapper.toDto(tag.get());
        } else throw new UserNotFoundException("No user with order", 40401);
    }
}
