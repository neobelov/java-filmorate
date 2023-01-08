package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FilmServiceClass implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film likeFilm(Integer filmId, Integer userId) {
        User user = userStorage.getById(userId);
        return filmStorage.getById(filmId).addLike(user.getId());
    }

    public Film unlikeFilm(Integer filmId, Integer userId) {
        User user = userStorage.getById(userId);
        return filmStorage.getById(filmId).deleteLike(user.getId());
    }

    public List<Film> getMostPopularFilms(Optional<Integer> count) {
        List<Film> filmsToSort = filmStorage.getAll();
        filmsToSort.sort((o1, o2) -> {
            if (o1.getUsersWhoLiked().size() - o2.getUsersWhoLiked().size() != 0) {
                return o2.getUsersWhoLiked().size() - o1.getUsersWhoLiked().size();
            } else {
                return o1.getId() - o2.getId();
            }
        });
        if (count.isPresent()) {
            return filmsToSort.subList(0,Integer.min(count.get(),filmsToSort.size()));
        } else {
            return filmsToSort.subList(0,10);
        }
    }
}
