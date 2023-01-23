package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.storage.common.Storage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.util.List;

@Component
public class MPADbStorage extends DbStorage<MPA> implements MPAStorage {
    public MPADbStorage(JdbcTemplate jdbcTemplate) {super(jdbcTemplate);}

    @Override
    public MPA add(MPA obj) {
        return super.add(obj);
    }

    @Override
    public MPA update(MPA obj) {
        return super.update(obj);
    }

    @Override
    public MPA delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<MPA> getAll() {
        String sql =
                "SELECT m.* " +
                "FROM mpa_ratings AS m " +
                "ORDER BY m.MPA_RATING_ID";
        return jdbcTemplate.query(sql, Maker::makeMPA);
    }

    @Override
    public MPA getById(Integer id) {
        try {
            String sql =
                    "SELECT m.* " +
                            "FROM mpa_ratings AS m " +
                            "WHERE m.MPA_rating_id = ?";
            return jdbcTemplate.queryForObject(sql, Maker::makeMPA, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }
}
