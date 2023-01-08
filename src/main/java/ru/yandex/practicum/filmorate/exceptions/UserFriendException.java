package ru.yandex.practicum.filmorate.exceptions;

public class UserFriendException extends RuntimeException {
    public UserFriendException(String message) {
        super(message);
    }
}