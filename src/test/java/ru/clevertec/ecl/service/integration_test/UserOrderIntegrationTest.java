package ru.clevertec.ecl.service.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.dto.UserOrderedDto;
import ru.clevertec.ecl.service.data.TagTestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserOrderIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final String URI = "/userOrder";

    @Test
    void checkCreate() throws Exception {
        UserOrderedDto userOrderedDto = UserOrderedDto.builder()
                .userName("Andrei")
                .giftName("fifth")
                .build();
        String userOrderedDtoJson = objectMapper.writeValueAsString(userOrderedDto);

        mockMvc.perform(post(URI + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userOrderedDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void checkGetMostFrequencyUsedByMostActiveUser() throws Exception {
        TagDto tagDto = TagTestBuilder.aTag().withName("relax").buildDto();
        String tagDtoJson = objectMapper.writeValueAsString(tagDto);

        mockMvc.perform(get(URI + "/mostUsedTag"))
                .andExpect(content().json(tagDtoJson));
    }
}
