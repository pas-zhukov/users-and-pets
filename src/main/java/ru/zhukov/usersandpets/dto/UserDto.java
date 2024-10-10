package ru.zhukov.usersandpets.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public class UserDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @Min(0L)
    @Max(150L)
    @NotNull
    private Integer age;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @NotBlank String getName() {
        return name;
    }

    public void setName(@NotNull @NotBlank String name) {
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
}
