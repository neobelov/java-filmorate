package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserService {
    User addFriend(Integer whoAddsId, Integer whoToAddId);

    User deleteFriend(Integer whoDeletesId, Integer whoToDeleteId);

    List<User> getCommonFriends(Integer user1id, Integer user2id);

    List<User> getFriends(Integer userId);
}
