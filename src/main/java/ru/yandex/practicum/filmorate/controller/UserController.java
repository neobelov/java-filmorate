package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer nextId = 0;

    @PostMapping("/users")
    public User add(@Valid @RequestBody User user) {
        if (user.isValid()) {
            user.setId(++nextId);
            users.put(nextId, user);
            log.info("added user " + user);
        }
        return user;
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        if (user.isValid() & users.containsKey(user.getId())) {
            users.put(nextId, user);
            log.info("updated user " + user);
        } else {
            throw new ResourceNotFoundException("User for update is not found");
        }
        return user;
    }
}