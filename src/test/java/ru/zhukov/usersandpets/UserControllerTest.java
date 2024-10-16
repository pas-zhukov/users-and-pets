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
import ru.zhukov.usersandpets.dto.PetDto;
import ru.zhukov.usersandpets.dto.UserDto;
import ru.zhukov.usersandpets.service.PetService;
import ru.zhukov.usersandpets.service.UserService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldSuccessOnUserWithoutPetCreated() throws Exception {
        UserDto user = new UserDto(null,
                "Pasha",
                "pas-zhukov@yandex.ru",
                25,
                null);
        String userJson = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertEquals(user.getName(), createdUser.getName());
        Assertions.assertEquals(user.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(user.getAge(), createdUser.getAge());
        Assertions.assertNotNull(createdUser.getPets());
        Assertions.assertTrue(createdUser.getPets().isEmpty());
    }

    @Test
    public void shouldNotCreateUserWithPet() throws Exception {
        UserDto user = new UserDto(null,
                "Pasha",
                "pas-zhukov@yandex.ru",
                25,
                List.of(new PetDto(null, "Jack", null)));
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void shouldSuccessGetUserById() throws Exception {
        UserDto user = userService.createUser("Pasha", "pas-zhukov@yandex.ru", 25);
        PetDto pet = petService.createPet("Bobik", user.getId());
        user.setPets(List.of(pet));

        String gotUserJson = mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto gotUser = objectMapper.readValue(gotUserJson, UserDto.class);

        org.assertj.core.api.Assertions.assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(gotUser);
    }

    @Test
    public void shouldNotCreateUserWhenRequestIsInvalid() throws Exception {
        UserDto user = new UserDto(null,
                "",
                "@ru.mail",
                -100,
                null);
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void shouldReturnNotFoundWhenUserNotPresent() throws Exception {
        mockMvc.perform(get("/users/{id}", Long.MAX_VALUE))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void shouldReturnBadRequestWhenTryToDeleteUserWithPets() throws Exception {
        UserDto createdUser = userService.createUser("Pasha",
                "pas-zhukov@yandex.ru", 25);
        petService.createPet("Jack", createdUser.getId());

        mockMvc.perform(delete("/users/" + createdUser.getId()))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

    }

}
