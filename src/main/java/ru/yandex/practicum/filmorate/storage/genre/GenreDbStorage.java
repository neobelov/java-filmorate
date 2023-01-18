package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.util.List;

@Component
public class GenreDbStorage extends DbStorage<Genre> implements GenreStorage {
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public Genre add(Genre obj) {
        return super.add(obj);
    }

    @Override
    public Genre update(Genre obj) {
        return super.update(obj);
    }

    @Override
    public Genre delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<Genre> getAll() {
        String sql =
                "SELECT g.* " +
                "FROM genres AS g ";
        return jdbcTemplate.query(sql, Maker::makeGenre);
    }

    @Override
    public Genre getById(Integer id) {
        try {
            String sql =
                    "SELECT g.* " +
                            "FROM genres AS g " +
                            "WHERE g.genre_id = ?";
            return jdbcTemplate.queryForObject(sql, Maker::makeGenre, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }
}
