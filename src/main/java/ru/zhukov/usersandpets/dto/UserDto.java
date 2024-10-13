package ru.zhukov.usersandpets.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

public class UserDto {
    @Null
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @Positive
    @Max(150L)
    @NotNull
    private Integer age;
    @Valid
    private List<PetDto> pets;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, Integer age, List<PetDto> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    public @Null Long getId() {
        return id;
    }

    public void setId(@Null Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email String email) {
        this.email = email;
    }

    public @Min(0L) @Max(150L) @NotNull Integer getAge() {
        return age;
    }

    public void setAge(@Min(0L) @Max(150L) @NotNull Integer age) {
        this.age = age;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }

    public void addPet(PetDto pet) {
        this.pets.add(pet);
    }

    public void removePet(PetDto pet) {
        this.pets.remove(pet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(age, userDto.age) && Objects.equals(pets, userDto.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, pets);
    }
}
