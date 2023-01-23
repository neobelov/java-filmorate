package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.util.List;
@Component
public class LikeDbStorage extends DbStorage<Like> implements LikeStorage {
    public LikeDbStorage(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public void deleteByFilmAndUserId(Integer filmId, Integer userId) {
        String sql = "DELETE FROM film_likes WHERE film_id = ? AND user_who_liked_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public Like delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<Like> getAll() {
        String sql =
                "SELECT fl.* " +
                "FROM film_likes AS fl ";
        return jdbcTemplate.query(sql, Maker::makeLike);
    }

    @Override
    public Like getById(Integer id) {
        try {
            String sql =
                    "SELECT fl.* " +
                            "FROM film_likes AS fl " +
                            "WHERE fl.id = ?";
            return jdbcTemplate.queryForObject(sql, Maker::makeLike, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }
}
