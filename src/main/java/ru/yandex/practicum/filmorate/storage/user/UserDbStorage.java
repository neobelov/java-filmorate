package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.util.List;

@Component
public class UserDbStorage extends DbStorage<User> implements UserStorage {
    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public User add(User obj) {
        return super.add(obj);
    }

    @Override
    public User update(User obj) {
        return super.update(obj);
    }

    @Override
    public User delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<User> getAll() {
        String sql =
                "SELECT u.*, ARRAY_AGG (uf.FRIEND_ID) AS friend_ids, ARRAY_AGG (uf.IS_CONFIRMED) as is_confirmed " +
                "FROM users AS u " +
                "LEFT JOIN USER_FRIENDS UF on u.USER_ID = UF.USER_ID " +
                "GROUP BY u.user_id";
        return jdbcTemplate.query(sql, Maker::makeUser);
    }

    @Override
    public User getById(Integer id) {
        try {
            String sql =
                "SELECT u.*, ARRAY_AGG (uf.FRIEND_ID) AS friend_ids, ARRAY_AGG (uf.IS_CONFIRMED) as is_confirmed " +
                "FROM users AS u " +
                "LEFT JOIN USER_FRIENDS UF on u.USER_ID = UF.USER_ID " +
                "WHERE u.user_id = ?" +
                "GROUP BY u.user_id";
            return jdbcTemplate.queryForObject(sql, Maker::makeUser, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }

    @Override
    public List<User> getFriends(Integer id) {
        String sql =
            "SELECT u.*, ARRAY_AGG (uf.FRIEND_ID) AS friend_ids, ARRAY_AGG (uf.IS_CONFIRMED) as is_confirmed " +
            "FROM user_friends AS uf " +
            "LEFT JOIN users u on u.USER_ID = UF.FRIEND_ID " +
            "WHERE uf.user_id = ?" +
            "GROUP BY uf.user_id, u.USER_ID";
        return jdbcTemplate.query(sql, Maker::makeUser, id);
    }

    @Override
    public List<User> getCommonFriends(Integer user1id, Integer user2id) {
        String sql =
                "SELECT u.*, ARRAY_AGG (uf.FRIEND_ID) AS friend_ids, ARRAY_AGG (uf.IS_CONFIRMED) as is_confirmed " +
                "FROM user_friends as uf " +
                "LEFT JOIN users u on u.user_id = uf.friend_id " +
                "WHERE uf.user_id = ?" +
                "AND uf.friend_id IN (SELECT friend_id FROM user_friends WHERE user_id = ?) " +
                "GROUP BY uf.user_id";
        return jdbcTemplate.query(sql, Maker::makeUser, user1id, user2id);
    }
}
