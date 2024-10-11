package ru.zhukov.usersandpets.service;

import org.springframework.stereotype.Service;
import ru.zhukov.usersandpets.dto.PetDto;
import ru.zhukov.usersandpets.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    public Boolean isUserPetsUpdateValid(UserDto currentUser, UserDto updatedUser) {
        return null;
    }

    public void deleteUser(Long userId) {
        UserDto user = getUserById(userId);
        if (!user.getPets().isEmpty()) {
            throw new IllegalArgumentException("User with pets can't be deleted");
        } else {
            users.remove(userId);
        }
    }

    public UserDto addPetToUser(Long userId, PetDto pet) {
        UserDto user = getUserById(userId);
        user.getPets().add(pet);
        return user;
    }

    public UserDto removePetFromUser(Long userId, Long petId) {
        UserDto user = getUserById(userId);
        user.getPets().removeIf(pet -> pet.getId().equals(petId));
        return user;
    }

    public UserDto getUserById(Long userId) {
        try {
            return users.get(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("User with id %s not found".formatted(userId));
        }
    }

    public List<UserDto> getAllUsers() {
        return users.values().stream().toList();
    }
}
