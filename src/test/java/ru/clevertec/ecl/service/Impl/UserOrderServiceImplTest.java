package ru.clevertec.ecl.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import ru.clevertec.ecl.service.data.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserOrderServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository giftRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private UserOrderServiceImpl userOrderService;

    @Test
    void createOrderSuccessfullyUserSave() {
        String userName = "user";
        String giftName = "gift";
        User user = UserTestBuilder.aUser().withName(userName).build();
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().withName(giftName).build();
        UserOrderedDto userOrderedDto = UserOrderTestBuilder.aUserOrder().withUserName(userName).withGiftName(giftName).buildDto();
        Order order = OrderTestBuilder.aOrder().withId(null).withUser(user).withGiftCertificate(gift).withPrice(gift.getPrice()).build();

        Mockito.doReturn(Optional.of(user))
                .when(userRepository).findByName(userName);

        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findByName(giftName);

        userOrderService.createOrder(userOrderedDto);

        Mockito.verify(orderRepository).save(order);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    void createOrderShouldThrowUserNotFoundException() {
        String userName = "user";
        String giftName = "gift";
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().withName(giftName).build();
        UserOrderedDto userOrderedDto = UserOrderTestBuilder.aUserOrder().withUserName(userName).withGiftName(giftName).buildDto();

        Mockito.doReturn(Optional.empty())
                .when(userRepository).findByName(userName);

        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findByName(giftName);

        assertThrows(UserNotFoundException.class,
                () -> userOrderService.createOrder(userOrderedDto));
    }

    @Test
    void createOrderShouldThrowGiftCertificateNotFoundException() {
        String userName = "user";
        String giftName = "gift";
        User user = UserTestBuilder.aUser().withName(userName).build();
        UserOrderedDto userOrderedDto = UserOrderTestBuilder.aUserOrder().withUserName(userName).withGiftName(giftName).buildDto();

        Mockito.doReturn(Optional.of(user))
                .when(userRepository).findByName(userName);

        Mockito.doReturn(Optional.empty())
                .when(giftRepository).findByName(giftName);

        assertThrows(GiftCertificateNotFoundException.class,
                () -> userOrderService.createOrder(userOrderedDto));
    }

    @Test
    void getUserOrdersSuccessfully() {
        String userName = "some name";
        PageRequest pageRequest = PageRequest.of(5, 5);
        Order order1 = OrderTestBuilder.aOrder().build();
        Order order2 = OrderTestBuilder.aOrder().build();
        Order order3 = OrderTestBuilder.aOrder().build();
        List<Order> list = List.of(order1, order2, order3);
        User user = UserTestBuilder.aUser().withOrders(list).withName(userName).build();
        Page<Order> actual = new PageImpl<>(list);

        Mockito.doReturn(Optional.of(user))
                .when(userRepository).findByName(userName);

        Mockito.doReturn(actual)
                .when(orderRepository).findAllByUserIs(user, pageRequest);

        Page<OrderDto> expected = userOrderService.getUserOrders(userName, pageRequest);
        assertThat(expected.getTotalElements()).isEqualTo(actual.getTotalElements());
    }

    @Test
    void getUserOrdersShouldThrowUserNotFoundException() {
        String userName = "some name";
        PageRequest pageRequest = PageRequest.of(5, 5);

        Mockito.doReturn(Optional.empty())
                .when(userRepository).findByName(userName);

        assertThrows(UserNotFoundException.class,
                () -> userOrderService.getUserOrders(userName, pageRequest));
    }

    @Test
    void findMostFrequencyUsedByMostActiveUserSuccessfully() {
        Tag tag = TagTestBuilder.aTag().build();
        TagDto actual = TagTestBuilder.aTag().buildDto();

        Mockito.doReturn(Optional.of(tag))
                .when(tagRepository).findMostFrequencyUsedByMostActiveUser();

        Mockito.doReturn(actual)
                .when(tagMapper).toDto(tag);

        TagDto expected = userOrderService.findMostFrequencyUsedByMostActiveUser();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void findMostFrequencyUsedByMostActiveUserShouldThrowUserNotFoundException() {
        Mockito.doReturn(Optional.empty())
                .when(tagRepository).findMostFrequencyUsedByMostActiveUser();

        assertThrows(UserNotFoundException.class,
                () -> userOrderService.findMostFrequencyUsedByMostActiveUser());
    }
}
