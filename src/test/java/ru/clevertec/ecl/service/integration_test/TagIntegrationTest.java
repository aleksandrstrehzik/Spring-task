package ru.clevertec.ecl.service.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.service.data.TagTestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class TagIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final String URI = "/tag";

    @Test
    void checkGetByName() throws Exception {
        String name = "relax";
        TagDto tagDto = TagTestBuilder.aTag()
                .withName(name)
                .buildDto();
        String tagDtoJson = objectMapper.writeValueAsString(tagDto);

        mockMvc.perform(get(URI + "/getn/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().json(tagDtoJson));
    }

    @Test
    void checkGetById() throws Exception {
        Long id = 2L;
        TagDto tagDto = TagTestBuilder.aTag()
                .withName("relax")
                .buildDto();
        String tagDtoJson = objectMapper.writeValueAsString(tagDto);

        mockMvc.perform(get(URI + "/geti/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(tagDtoJson));
    }

    @Test
    void checkCreate() throws Exception {
        TagDto tagDto = TagTestBuilder.aTag()
                .withName("enjoy")
                .buildDto();
        String tagDtoJson = objectMapper.writeValueAsString(tagDto);

        mockMvc.perform(post(URI + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tagDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void checkDelete() throws Exception {
        String name = "awfully";

        mockMvc.perform(delete(URI + "/{name}", name))
                .andExpect(status().isAccepted());
    }
}
