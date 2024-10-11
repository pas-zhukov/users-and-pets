package ru.zhukov.usersandpets.service;

import org.springframework.stereotype.Service;
import ru.zhukov.usersandpets.dto.PetDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetService {

    private final UserService userService;

    private final Map<Long, PetDto> pets = new HashMap<>();
    private Long idCounter = 0L;

    public PetService(UserService userService) {
        this.userService = userService;
    }

    public PetDto createPet(String name, Long userId) {
        PetDto pet = new PetDto(++idCounter, name, userId);
        pets.put(pet.getId(), pet);
        return pet;
    }
    public PetDto updatePet(Long petId, PetDto newPet) {
        PetDto pet = getPetById(petId);
        pet.setName(newPet.getName());
        userService.removePetFromUser(pet.getUserId(), petId);
        userService.addPetToUser(newPet.getUserId(), newPet);
        return pet;
    }

    public void deletePet(Long petId) {
        userService.removePetFromUser(getPetById(petId).getUserId(), petId);
        pets.remove(petId);
    }

    public PetDto getPetById(Long petId) {
        try {
            return pets.get(petId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Pet with id %s not found".formatted(petId));
        }
    }

    public List<PetDto> getAllPets() {
        return pets.values().stream().toList();
    }
}
