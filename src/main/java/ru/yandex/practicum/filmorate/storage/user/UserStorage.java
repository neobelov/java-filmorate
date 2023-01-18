package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.common.Storage;

import java.util.List;

public interface UserStorage extends Storage <User> {
    List<User> getFriends(Integer id);
    List<User> getCommonFriends(Integer user1id, Integer user2id);
}
