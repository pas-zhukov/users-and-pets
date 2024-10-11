package ru.zhukov.usersandpets.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {
    @Min(1L)
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Min(0L)
    private Long userId;

    public PetDto() {
    }

    public PetDto(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
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

    public @NotNull @Min(0L) Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull @Min(0L) Long userId) {
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
