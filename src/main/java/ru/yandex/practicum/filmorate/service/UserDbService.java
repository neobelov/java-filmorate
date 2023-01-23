package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDbService implements UserService {
    private final FriendStorage friendStorage;
    private final UserStorage userStorage;

    @Override
    public User addFriend(Integer whoAddsId, Integer whoToAddId) {
        Friend friend = new Friend(whoAddsId, whoToAddId);
        friendStorage.add(friend);
        return userStorage.getById(whoAddsId);
    }

    @Override
    public User deleteFriend(Integer whoDeletesId, Integer whoToDeleteId) {
        friendStorage.deleteByUserIds(whoDeletesId, whoToDeleteId);
        return userStorage.getById(whoDeletesId);
    }

    @Override
    public List<User> getCommonFriends(Integer user1id, Integer user2id) {
        return userStorage.getCommonFriends(user1id, user2id);
    }

    @Override
    public List<User> getFriends(Integer userId) {
        return userStorage.getFriends(userId);
    }
}
