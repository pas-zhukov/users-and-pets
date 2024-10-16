package ru.zhukov.usersandpets.service;

import org.springframework.stereotype.Service;
import ru.zhukov.usersandpets.dto.PetDto;

import java.util.*;

@Service
public class PetService {

    private final UserService userService;

    private final Map<Long, PetDto> pets = new HashMap<>();
    private Long idCounter = 0L;

    public PetService(UserService userService) {
        this.userService = userService;
    }

    public PetDto createPet(String name, Long userId) {
        userService.raiseOnUserDoesNotExist(userId);
        PetDto pet = new PetDto(++idCounter, name, userId);
        pets.put(pet.getId(), pet);
        userService.addPetToUser(userId, pet);
        return pet;
    }

    public PetDto updatePet(Long petId, PetDto newPet) {
        userService.raiseOnUserDoesNotExist(newPet.getUserId());
        PetDto pet = getPetById(petId);
        pet.setName(newPet.getName());
        userService.removePetFromUser(pet.getUserId(), petId);
        userService.addPetToUser(newPet.getUserId(), newPet);
        return pet;
    }

    public void deletePet(Long petId) {
        raiseOnPetDoesNotExist(petId);
        userService.removePetFromUser(getPetById(petId).getUserId(), petId);
        pets.remove(petId);
    }

    public PetDto getPetById(Long petId) {
        return Optional.ofNullable(pets.get(petId)).orElseThrow(() -> new NoSuchElementException("User with id %s not found".formatted(petId)));
    }

    public List<PetDto> getAllPets() {
        return pets.values().stream().toList();
    }

    public PetDto createPet(PetDto petToCreate) {
        return createPet(petToCreate.getName(), petToCreate.getUserId());
    }

    public void raiseOnPetDoesNotExist(Long petId) {
        getPetById(petId);
    }
}
