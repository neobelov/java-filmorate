package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.common.Storage;

import java.util.List;
import java.util.Optional;

public interface LikeStorage extends Storage<Like> {
    void deleteByFilmAndUserId(Integer filmId, Integer userId);
}
