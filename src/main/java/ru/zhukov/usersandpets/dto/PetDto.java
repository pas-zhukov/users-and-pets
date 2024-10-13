package ru.zhukov.usersandpets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {
    @Null
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    @Positive
    private Long userId;

    public PetDto() {
    }

    public PetDto(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public @NotNull @Positive Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull @Positive Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDto petDto = (PetDto) o;
        return Objects.equals(id, petDto.id) && Objects.equals(name, petDto.name) && Objects.equals(userId, petDto.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId);
    }
}
