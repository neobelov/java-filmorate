package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.sql.SQLException;
import java.util.List;

@Component
public class FriendDbStorage extends DbStorage<Friend> implements FriendStorage {
    public FriendDbStorage(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public Friend add (Friend obj) {
        try {
            return super.add(obj);
        } catch (DataAccessException exp) {
            throw new ResourceNotFoundException("trying to befriend non-existent user or users");
        }
    }
    @Override
    public Friend delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<Friend> getAll() {
        String sql =
                "SELECT uf.* " +
                "FROM user_friends AS uf ";
        return jdbcTemplate.query(sql, Maker::makeFriend);
    }

    @Override
    public Friend getById(Integer id) {
        try {
            String sql =
                    "SELECT uf.* " +
                    "FROM user_friends AS uf " +
                    "WHERE uf.id = ?";
            return jdbcTemplate.queryForObject(sql, Maker::makeFriend, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }

    @Override
    public void deleteByUserIds(Integer whoDeletesId, Integer whoToDeleteId) {
        String sql = "DELETE FROM user_friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, whoDeletesId, whoToDeleteId);
    }
}
