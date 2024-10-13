package ru.zhukov.usersandpets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.zhukov.usersandpets.dto.PetDto;
import ru.zhukov.usersandpets.dto.UserDto;
import ru.zhukov.usersandpets.service.PetService;
import ru.zhukov.usersandpets.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldSuccessCreatePet() throws Exception {
        UserDto user = userService.createUser("Паша", "pas-zhukov@yandex.ru", 25);
        PetDto pet = new PetDto(null, "Шарик", user.getId());

        String petJson = objectMapper.writeValueAsString(pet);

        String createdPetJson = mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto createdPet = objectMapper.readValue(createdPetJson, PetDto.class);

        Assertions.assertNotNull(createdPet.getId());
        Assertions.assertEquals(pet.getName(), createdPet.getName());
    }

    @Test
    public void shouldSuccessGetPetById() throws Exception {
        UserDto user = userService.createUser("Паша", "pas-zhukov@yandex.ru", 25);
        PetDto pet = petService.createPet("Бобик", user.getId());

        String gotPetJson = mockMvc.perform(get("/pets/{id}", pet.getId()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto gotPet = objectMapper.readValue(gotPetJson, PetDto.class);

        org.assertj.core.api.Assertions.assertThat(pet)
                .usingRecursiveComparison()
                .isEqualTo(gotPet);
    }

    @Test
    public void shouldSuccessUpdatePet() throws Exception {
        throw new Exception();
    }

    @Test
    public void shouldNotCreatePetWhenRequestNotValid() throws Exception{
        PetDto pet = new PetDto(null, "", null);
        String petJson = objectMapper.writeValueAsString(pet);

        mockMvc.perform(post("/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void shouldReturnNotFoundWhenPetNotPresent() throws Exception {
        mockMvc.perform(get("/pets/{id}", Long.MAX_VALUE))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

}
