package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film likeFilm(Integer filmId, Integer userId);

    Film unlikeFilm(Integer filmId, Integer userId);

    List<Film> getMostPopularFilms(Optional<Integer> count);
}
