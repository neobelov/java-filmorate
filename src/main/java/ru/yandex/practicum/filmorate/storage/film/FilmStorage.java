package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.common.Storage;

import java.util.List;
import java.util.Optional;

public interface FilmStorage extends Storage<Film> {
    List<Film> getMostPopularFilms(Optional<Integer> count);
}
