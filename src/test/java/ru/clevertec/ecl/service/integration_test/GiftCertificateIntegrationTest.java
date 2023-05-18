package ru.clevertec.ecl.service.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.data.GiftCertificateTestBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class GiftCertificateIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final String URI = "/gift";

    @Test
    void checkGetByName() throws Exception {
        String name = "eighth";
        GiftCertificateDto giftDto = GiftCertificateTestBuilder.aGift()
                .withName(name)
                .withCreateDate(Instant.parse("2023-04-25T14:24:27.613463000Z"))
                .withDescription("eighth gift")
                .withDuration(100)
                .withPrice(BigDecimal.valueOf(9832, 2))
                .buildDto();
        String giftDtoJson = objectMapper.writeValueAsString(giftDto);

        mockMvc.perform(get(URI + "/getn/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().json(giftDtoJson));
    }

    @Test
    void checkGetById() throws Exception {
        Long id = 8L;
        GiftCertificateDto giftDto = GiftCertificateTestBuilder.aGift()
                .withName("eighth")
                .withCreateDate(Instant.parse("2023-04-25T14:24:27.613463000Z"))
                .withDescription("eighth gift")
                .withDuration(100)
                .withPrice(BigDecimal.valueOf(9832, 2))
                .buildDto();
        String giftDtoJson = objectMapper.writeValueAsString(giftDto);

        mockMvc.perform(get(URI + "/geti/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(giftDtoJson));
    }

    @Test
    void checkDelete() throws Exception {
        String name = "first";

        mockMvc.perform(delete(URI + "/{name}", name))
                .andExpect(status().isAccepted());
    }

    @Test
    void checkCreate() throws Exception {
        GiftCertificateDto giftDto = GiftCertificateTestBuilder.aGift()
                .withName("ninth")
                .withCreateDate(Instant.parse("2023-04-25T14:24:27.613463000Z"))
                .withDescription("eighth gift")
                .withDuration(100)
                .withPrice(BigDecimal.valueOf(9832, 2))
                .buildDto();
        String giftDtoJson = objectMapper.writeValueAsString(giftDto);

        mockMvc.perform(post(URI + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void checkUpdate() throws Exception {
        GiftCertificateDto giftDto = GiftCertificateTestBuilder.aGift()
                .withName("second")
                .withDescription("change description")
                .buildDto();
        String giftDtoJson = objectMapper.writeValueAsString(giftDto);

        mockMvc.perform(patch(URI + "/patch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(giftDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description").value(giftDto.getDescription()))
                .andExpect(jsonPath("name").value(giftDto.getName()))
                .andExpect(jsonPath("tags").isNotEmpty());
    }

    @Test
    void checkFindAll() throws Exception {
        GiftCertificateDto giftDto1 = GiftCertificateTestBuilder.aGift()
                .withName("second")
                .withCreateDate(Instant.parse("2023-04-25T14:24:27.613463000Z"))
                .withDescription("second gift")
                .withDuration(100)
                .withPrice(BigDecimal.valueOf(9832, 2))
                .buildDto();
        GiftCertificateDto giftDto2 = GiftCertificateTestBuilder.aGift()
                .withName("second")
                .withCreateDate(Instant.parse("2023-04-25T14:24:27.613463000Z"))
                .withDescription("second gift")
                .withDuration(100)
                .withPrice(BigDecimal.valueOf(9832, 2))
                .buildDto();
        List<GiftCertificateDto> listDto = List.of(giftDto1, giftDto2);
        String giftDtoJson = objectMapper.writeValueAsString(listDto);

        mockMvc.perform(get(URI + "/findAll")
                        .param("name", "o"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value(giftDtoJson));
    }
}
