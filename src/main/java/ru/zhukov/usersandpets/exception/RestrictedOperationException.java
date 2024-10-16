package ru.zhukov.usersandpets.exception;

public class RestrictedOperationException extends RuntimeException {
    public RestrictedOperationException(String message) {
        super(message);
    }
}
