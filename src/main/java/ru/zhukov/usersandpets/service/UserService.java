package ru.zhukov.usersandpets.service;

import org.springframework.stereotype.Service;
import ru.zhukov.usersandpets.dto.PetDto;
import ru.zhukov.usersandpets.dto.UserDto;
import ru.zhukov.usersandpets.exception.RestrictedOperationException;

import java.util.*;

@Service
public class UserService {

    private final Map<Long, UserDto> users = new HashMap<>();
    private Long idCounter = 0L;

    public UserDto createUser(String name, String mail, int age) {
        UserDto user = new UserDto(++idCounter, name, mail, age, new ArrayList<>());
        users.put(user.getId(), user);
        return user;
    }

    public UserDto updateUser(Long userId, UserDto newUser) {
        UserDto user = getUserById(userId);
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setAge(newUser.getAge());
        return user;
    }

    public void deleteUser(Long userId) {
        UserDto user = getUserById(userId);
        if (!user.getPets().isEmpty()) {
            throw new RestrictedOperationException("User with pets can't be deleted");
        } else {
            users.remove(userId);
        }
    }

    public void addPetToUser(Long userId, PetDto pet) {
        UserDto user = getUserById(userId);
        user.addPet(pet);
        pet.setUserId(userId);
    }

    public void removePetFromUser(Long userId, Long petId) {
        UserDto user = getUserById(userId);
        user.getPets().removeIf(pet -> pet.getId().equals(petId));
    }

    public UserDto getUserById(Long userId) throws NoSuchElementException {
        return Optional.ofNullable(users.get(userId)).orElseThrow(() -> new NoSuchElementException("User with id %s not found".formatted(userId)));
    }

    public List<UserDto> getAllUsers() {
        return users.values().stream().toList();
    }

    public UserDto createUser(UserDto userToCreate) {
        return createUser(userToCreate.getName(), userToCreate.getEmail(), userToCreate.getAge());
    }

    public void raiseOnUserDoesNotExist(Long userId) {
        getUserById(userId);
    }
}
