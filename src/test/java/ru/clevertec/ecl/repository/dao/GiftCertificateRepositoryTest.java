package ru.clevertec.ecl.repository.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.ecl.service.integration_test.BaseIntegrationTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class GiftCertificateRepositoryTest extends BaseIntegrationTest {

    private final GiftCertificateRepository giftRepository;

    @ParameterizedTest
    @MethodSource("getPageRequestAndExpectedSize")
    void checkFindAllOrAllByPartNameWithEmptyName(PageRequest pageRequest, int expected) {
        int actual = giftRepository.findAllOrAllByPartName("", pageRequest).getNumberOfElements();

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("getPageRequestAndExpectedSizeAndPartOfName")
    void checkFindAllOrAllByPartNameWithPartOfName(PageRequest pageRequest, int expected, String name) {
        int actual = giftRepository.findAllOrAllByPartName(name, pageRequest).getNumberOfElements();

        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getPageRequestAndExpectedSize() {
        return Stream.of(
                arguments(PageRequest.of(0, 5), 5),
                arguments(PageRequest.of(1, 5), 3),
                arguments(PageRequest.of(2, 5), 0));
    }

    static Stream<Arguments> getPageRequestAndExpectedSizeAndPartOfName() {
        return Stream.of(
                arguments(PageRequest.of(0, 5), 3, "f"),
                arguments(PageRequest.of(0, 5), 1, "c"),
                arguments(PageRequest.of(0, 5), 0, "]"));
    }

}