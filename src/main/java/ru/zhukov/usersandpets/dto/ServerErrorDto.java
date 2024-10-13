package ru.zhukov.usersandpets.dto;

import java.time.LocalDateTime;

public record ServerErrorDto(String message,
                             String detailedMessage,
                             LocalDateTime dateTime) {
}