package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.common.Storage;

public interface FriendStorage extends Storage<Friend> {
    void deleteByUserIds(Integer userWhoDeletesId, Integer whoToDeleteId);
}
