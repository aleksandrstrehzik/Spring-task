package ru.clevertec.ecl.service.integration_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.OrderDto;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.service.data.OrderTestBuilder;
import ru.clevertec.ecl.service.data.UserTestBuilder;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class UserIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final String URI = "/user";

    @Test
    void checkCreate() throws Exception {
        UserDto userDto = UserTestBuilder.aUser().withName("Ian").buildDto();

        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post(URI + "/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void checkGetAllUsers() throws Exception {
        UserDto userDto1 = UserTestBuilder.aUser().withName("Andrei").buildDto();
        UserDto userDto2 = UserTestBuilder.aUser().withName("Sergei").buildDto();
        UserDto userDto3 = UserTestBuilder.aUser().withName("Ekaterina").buildDto();
        UserDto userDto4 = UserTestBuilder.aUser().withName("Pavel").buildDto();
        List<UserDto> listDto = List.of(userDto1, userDto2, userDto3, userDto4);
        String giftDtoJson = objectMapper.writeValueAsString(listDto);

        mockMvc.perform(get(URI + "/getAll")
                        .param("name", "e"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data").value(giftDtoJson));
    }

    @Test
    void checkGetUserOrders() throws Exception {
        String name = "Pavel";
        OrderDto orderDto = OrderTestBuilder.aOrder()
                .withPrice(BigDecimal.valueOf(992, 2))
                .buildDto();

        mockMvc.perform(get(URI + "/getAllOrders")
                        .param("name", name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("price").value(orderDto.getPrice()))
                .andExpect(jsonPath("purchase").value(orderDto.getPurchase()));
    }

    @Test
    void checkGetByName() throws Exception {
        String name = "Andrei";
        UserDto userDto = UserTestBuilder.aUser().withName(name).buildDto();

        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(get(URI + "/getUser/{name}", name))
                .andExpect(status().isOk())
                .andExpect(content().json(userDtoJson));
    }
}
