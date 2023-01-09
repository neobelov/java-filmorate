package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @GetMapping("/films")
    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    @GetMapping("/films/{id}")
    public Film getById(@PathVariable Integer id) {
        return filmStorage.getById(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getMostPopularFilms(@RequestParam Optional<Integer> count) {
        return filmService.getMostPopularFilms(count);
    }

    @PostMapping("/films")
    public Film add(@Valid @RequestBody Film film) {
        return filmStorage.add(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film likeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/films/{id}")
    public Film delete(@PathVariable Integer id) {
        return filmStorage.delete(id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film unlikeFilm(@PathVariable Integer id, @PathVariable Integer userId) {
        return filmService.unlikeFilm(id, userId);
    }

}
