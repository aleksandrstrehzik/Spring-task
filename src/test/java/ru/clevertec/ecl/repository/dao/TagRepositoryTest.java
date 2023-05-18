package ru.clevertec.ecl.repository.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import ru.clevertec.ecl.service.integration_test.BaseIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class TagRepositoryTest extends BaseIntegrationTest {

    private final TagRepository tagRepository;

    @Test
    void findMostFrequencyUsedByMostActiveUser() {
        String name = tagRepository.findMostFrequencyUsedByMostActiveUser().get().getName();

        assertThat(name).isEqualTo("relax");
    }
}