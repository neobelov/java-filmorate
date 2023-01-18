package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FilmDbService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;
    @Override
    public Film likeFilm(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        Like like = new Like(filmId, userId);
        film.addLike(userId);
        likeStorage.add(like);
        return film;
    }

    @Override
    public Film unlikeFilm(Integer filmId, Integer userId) {
        Film film = filmStorage.getById(filmId);
        User user = userStorage.getById(userId);
        Like like = new Like(filmId, userId);
        film.deleteLike(userId);
        likeStorage.deleteByFilmAndUserId(filmId, userId);
        return film;
    }

    @Override
    public List<Film> getMostPopularFilms(Optional<Integer> count) {
        return filmStorage.getMostPopularFilms(count);
    }
}
