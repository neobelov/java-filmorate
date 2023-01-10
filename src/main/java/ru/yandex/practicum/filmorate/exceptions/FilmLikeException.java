package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;

public class FilmLikeException extends RuntimeException {
    public FilmLikeException(String message) {
        super(message);
    }
}
