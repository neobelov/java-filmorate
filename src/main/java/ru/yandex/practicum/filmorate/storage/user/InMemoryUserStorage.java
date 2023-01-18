package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.common.InMemoryStorage;

import java.util.List;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InMemoryUserStorage extends InMemoryStorage<User> implements UserStorage {
    @Override
    public List<User> getFriends(Integer id) {
        return null;
    }

    @Override
    public List<User> getCommonFriends(Integer user1id, Integer user2id) {
        return null;
    }
}
