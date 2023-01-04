package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
            throw new ResourceNotFoundException("Film for update is not found");
        }
        return film;
    }
}
