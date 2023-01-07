package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private Integer nextId = 0;

    @PostMapping("/films")
    public Film add(@Valid @RequestBody Film film) {
        if (film.isValid()) {
            film.setId(++nextId);
            films.put(nextId, film);
            log.info("added film " + film);
        }
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        if (film.isValid() & films.containsKey(film.getId())) {
            films.put(nextId, film);
            log.info("updated film " + film);
        } else {
            throw new ResourceNotFoundException(String.format("Film with id %d for update is not found", film.getId()));
        }
        return film;
    }
}
