package ru.zhukov.usersandpets.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zhukov.usersandpets.dto.PetDto;
import ru.zhukov.usersandpets.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDto> getPetById(@PathVariable("petId") Long petId) {
        PetDto petDto = petService.getPetById(petId);
        return ResponseEntity.ok(petDto);
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petToCreate) {
        PetDto createdPet = petService.createPet(petToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<PetDto> updatePet(@PathVariable("petId") Long petId,
                                            @RequestBody @Valid PetDto petToUpdate) {
        PetDto updatedPet = petService.updatePet(petId, petToUpdate);
        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable("petId") Long petId) {
        petService.deletePet(petId);
        return ResponseEntity.noContent().build();
    }

}
