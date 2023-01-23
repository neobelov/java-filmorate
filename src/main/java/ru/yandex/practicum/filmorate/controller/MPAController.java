package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPAStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MPAController {
    private final MPAStorage mpaStorage;

    @GetMapping("/mpa")
    public List<MPA> getAll() {
        return mpaStorage.getAll();
    }

    @GetMapping("/mpa/{id}")
    public MPA getById(@PathVariable Integer id) {
        return mpaStorage.getById(id);
    }

}
