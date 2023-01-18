package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.common.InMemoryStorage;

import java.util.*;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InMemoryFilmStorage extends InMemoryStorage<Film> implements FilmStorage {
    @Override
    public List<Film> getMostPopularFilms(Optional<Integer> count) {
        return null;
    }
}
