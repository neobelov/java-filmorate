package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GenreController {
    private final GenreStorage genreStorage;

    @GetMapping("/genres")
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    @GetMapping("/genres/{id}")
    public Genre getById(@PathVariable Integer id) {
        return genreStorage.getById(id);
    }
}
