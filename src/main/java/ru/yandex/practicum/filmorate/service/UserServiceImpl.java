package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    public User addFriend(Integer whoAddsId, Integer whoToAddId) {
        User whoAdds = userStorage.getById(whoAddsId);
        User whoToAdd = userStorage.getById(whoToAddId);
        whoToAdd.addFriend(whoAdds.getId());
        return whoAdds.addFriend(whoToAdd.getId());
    }

    public User deleteFriend(Integer whoDeletesId, Integer whoToDeleteId) {
        User whoDeletes = userStorage.getById(whoDeletesId);
        User whoToDelete = userStorage.getById(whoToDeleteId);
        whoToDelete.deleteFriend(whoDeletes.getId());
        return whoDeletes.deleteFriend(whoToDelete.getId());
    }

    public List<User> getCommonFriends(Integer user1id, Integer user2id) {
        User user1 = userStorage.getById(user1id);
        User user2 = userStorage.getById(user2id);
        Set<Integer> commonFriendsSet = new HashSet<>(user1.getFriends());
        commonFriendsSet.retainAll(user2.getFriends());
        List<User> commonFriends = userStorage.getAll();
        commonFriends.removeIf(x->!commonFriendsSet.contains(x.getId()));
        return commonFriends;
    }

    public List<User> getFriends(Integer userId) {
        List<User> friends = userStorage.getAll();
        Set<Integer> friendsSet = userStorage.getById(userId).getFriends();
        friends.removeIf(x->!friendsSet.contains(x.getId()));
        return friends;
    }
}
