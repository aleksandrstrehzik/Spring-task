package ru.clevertec.ecl.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.mappers.GiftCertificateMapper;
import ru.clevertec.ecl.exception.GiftCertificateNotFoundException;
import ru.clevertec.ecl.repository.dao.GiftCertificateRepository;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.service.data.GiftCertificateTestBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@ExtendWith(MockitoExtension.class)
class GiftCertificateCrudServiceImplTest {

    @Mock
    private GiftCertificateRepository giftRepository;

    @Mock
    private GiftCertificateMapper giftMapper;

    @InjectMocks
    private GiftCertificateCrudServiceImpl giftCrudService;

    @ParameterizedTest
    @MethodSource("getGiftAndGiftDtoWithName")
    void checkGetByName(GiftCertificate gift, GiftCertificateDto giftDto, String name) {
        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findByName(name);

        Mockito.doReturn(giftDto)
                .when(giftMapper).toDto(gift);

        assertThat(giftCrudService.getByName(name)).isEqualTo(giftMapper.toDto(gift));
    }

    @ParameterizedTest
    @MethodSource("getGiftName")
    void checkGetNameThrow(String name) {
        Mockito.doReturn(Optional.empty())
                .when(giftRepository).findByName(name);

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.getByName(name));
    }

    @ParameterizedTest
    @MethodSource("getGiftWithId")
    void checkGetById(GiftCertificate gift, GiftCertificateDto giftDto, Long id) {
        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findById(id);

        Mockito.doReturn(giftDto)
                .when(giftMapper).toDto(gift);

        assertThat(giftCrudService.getById(id)).isEqualTo(giftMapper.toDto(gift));
    }

    @ParameterizedTest
    @MethodSource("getId")
    void checkGetByIdThrow(Long id) {
        Mockito.doReturn(Optional.empty())
                .when(giftRepository).findById(id);

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.getById(id));
    }

    @ParameterizedTest
    @MethodSource("getGiftWithName")
    void checkDelete(GiftCertificate gift, String name) {
        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findByName(name);

        giftCrudService.delete(name);

        Mockito.verify(giftRepository).delete(gift);
    }

    @ParameterizedTest
    @MethodSource("getGiftName")
    void checkDeleteShouldThrow(String name) {
        Mockito.doReturn(Optional.empty())
                .when(giftRepository).findByName(name);

        assertThrows(GiftCertificateNotFoundException.class,
                () -> giftCrudService.delete(name));
    }

    @Test
    void checkCreate() {
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().build();
        GiftCertificateDto giftDto = GiftCertificateDto.builder()
                .name(gift.getName())
                .build();

        Mockito.doReturn(gift)
                .when(giftMapper).toEntity(giftDto);

        giftCrudService.create(giftDto);

        Mockito.verify(giftRepository).save(gift);
    }

    @Test
    void checkUpdateSuccessfully() {
        GiftCertificate gift = GiftCertificateTestBuilder.aGift().withTags(null).build();
        GiftCertificateDto giftDto = GiftCertificateDto.builder()
                .name(gift.getName())
                .build();

        Mockito.doReturn(Optional.of(gift))
                .when(giftRepository).findByName(gift.getName());

        Mockito.doReturn(giftDto)
                .when(giftMapper).toDto(gift);

        GiftCertificateDto updateGift = giftCrudService.update(giftMapper.toDto(gift));

        assertThat(updateGift).isEqualTo(giftDto);
    }

    @Test
    void getAllOrAllByPartOfName() {
        String some_name = "some name";
        PageRequest pageRequest = PageRequest.of(5, 5);
        GiftCertificate first = GiftCertificateTestBuilder.aGift().withName("first").build();
        GiftCertificate second = GiftCertificateTestBuilder.aGift().withName("second").build();
        GiftCertificate third = GiftCertificateTestBuilder.aGift().withName("third").build();
        List<GiftCertificate> list = List.of(first, second, third);
        Page<GiftCertificate> actual = new PageImpl<>(list);

        Mockito.doReturn(actual)
                .when(giftRepository).findAllOrAllByPartName(some_name, pageRequest);

        Page<GiftCertificateDto> expected = giftCrudService.getAllOrAllByPartOfName(some_name, pageRequest);
        assertThat(expected.getTotalElements()).isEqualTo(actual.getTotalElements());
    }

    static Stream<Arguments> getId() {
        return Stream.of(
                arguments(1L),
                arguments(43L),
                arguments(65L));
    }

    static Stream<Arguments> getGiftWithId() {
        GiftCertificate gift1 = GiftCertificateTestBuilder.aGift().withId(1L).build();
        GiftCertificate gift2 = GiftCertificateTestBuilder.aGift().withId(2L).build();
        GiftCertificate gift3 = GiftCertificateTestBuilder.aGift().withId(3L).build();
        GiftCertificateDto giftDto1 = GiftCertificateTestBuilder.aGift().buildDto();
        GiftCertificateDto giftDto2 = GiftCertificateTestBuilder.aGift().buildDto();
        GiftCertificateDto giftDto3 = GiftCertificateTestBuilder.aGift().buildDto();
        return Stream.of(
                arguments(gift1, giftDto1, 1L),
                arguments(gift2, giftDto2, 2L),
                arguments(gift3, giftDto3, 3L));
    }

    static Stream<Arguments> getGiftName() {
        return Stream.of(
                arguments("first"),
                arguments("second"),
                arguments("third"));
    }

    static Stream<Arguments> getGiftAndGiftDtoWithName() {
        GiftCertificate gift1 = GiftCertificateTestBuilder.aGift().withName("first").build();
        GiftCertificate gift2 = GiftCertificateTestBuilder.aGift().withName("second").build();
        GiftCertificate gift3 = GiftCertificateTestBuilder.aGift().withName("third").build();
        GiftCertificateDto giftDto1 = GiftCertificateTestBuilder.aGift().withName("first").buildDto();
        GiftCertificateDto giftDto2 = GiftCertificateTestBuilder.aGift().withName("first").buildDto();
        GiftCertificateDto giftDto3 = GiftCertificateTestBuilder.aGift().withName("first").buildDto();
        return Stream.of(
                arguments(gift1, giftDto1, "first"),
                arguments(gift2, giftDto2, "second"),
                arguments(gift3, giftDto3, "third"));
    }

    static Stream<Arguments> getGiftWithName() {
        GiftCertificate gift1 = GiftCertificateTestBuilder.aGift().withName("first").build();
        GiftCertificate gift2 = GiftCertificateTestBuilder.aGift().withName("second").build();
        GiftCertificate gift3 = GiftCertificateTestBuilder.aGift().withName("third").build();
        return Stream.of(
                arguments(gift1, "first"),
                arguments(gift2, "second"),
                arguments(gift3, "third"));
    }
}
