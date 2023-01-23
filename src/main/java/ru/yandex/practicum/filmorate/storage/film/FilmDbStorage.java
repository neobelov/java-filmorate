package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.common.DbStorage;
import ru.yandex.practicum.filmorate.utilities.Maker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class FilmDbStorage extends DbStorage<Film> implements FilmStorage {
    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Film add(Film obj) {
        return super.add(obj);
    }

    @Override
    public Film update(Film obj) {
        return super.update(obj);
    }

    @Override
    public Film delete(Integer id) {
        return super.deleteObj(getById(id));
    }

    @Override
    public List<Film> getAll() {
        String sql =
            "SELECT f.*, m.name AS MPA_rating_name, ARRAY_AGG (g.genre_id) AS genre_ids, ARRAY_AGG (g.name) as genre_names, ARRAY_AGG(fl.user_who_liked_id) as user_who_liked_id " +
            "FROM films AS f " +
            "LEFT JOIN film_genres AS fg ON fg.FILM_ID = f.FILM_ID " +
            "LEFT JOIN genres AS g ON g.GENRE_ID = fg.GENRE_ID " +
            "LEFT JOIN MPA_ratings AS m ON m.MPA_rating_id = f.MPA_rating_id " +
            "LEFT JOIN film_likes AS fl on f.film_id = fl.film_id " +
            "GROUP BY f.film_id " +
            "ORDER BY f.film_id";
        return jdbcTemplate.query(sql, Maker::makeFilm);
    }

    @Override
    public Film getById(Integer id) {
        try {
            String sql =
                    "SELECT f.*, m.name AS MPA_rating_name, ARRAY_AGG (g.genre_id) AS genre_ids, ARRAY_AGG (g.name) as genre_names, ARRAY_AGG(fl.user_who_liked_id) as user_who_liked_id " +
                    "FROM films AS f " +
                    "LEFT JOIN film_genres AS fg ON fg.FILM_ID = f.FILM_ID " +
                    "LEFT JOIN genres AS g ON g.GENRE_ID = fg.GENRE_ID " +
                    "LEFT JOIN MPA_ratings AS m ON m.MPA_rating_id = f.MPA_rating_id " +
                    "LEFT JOIN film_likes AS fl on f.film_id = fl.film_id " +
                    "WHERE f.film_id = ? " +
                    "GROUP BY f.film_id";
            return jdbcTemplate.queryForObject(sql, Maker::makeFilm, id);
        } catch (EmptyResultDataAccessException exp) {
            throw new ResourceNotFoundException("Object with id " + id + " is not found");
        }
    }

    @Override
    public List<Film> getMostPopularFilms(Optional<Integer> count) {
        String sql =
                "SELECT f.*, m.name AS MPA_rating_name, ARRAY_AGG (g.genre_id) AS genre_ids, ARRAY_AGG (g.name) as genre_names, ARRAY_AGG(fl.user_who_liked_id) as user_who_liked_id, COUNT(fl.user_who_liked_id) " +
                "FROM films AS f " +
                "LEFT JOIN film_genres AS fg ON fg.FILM_ID = f.FILM_ID " +
                "LEFT JOIN genres AS g ON g.GENRE_ID = fg.GENRE_ID " +
                "LEFT JOIN MPA_ratings AS m ON m.MPA_rating_id = f.MPA_rating_id " +
                "LEFT JOIN film_likes AS fl on f.film_id = fl.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(fl.user_who_liked_id) DESC " +
                "LIMIT ?";
        if (count.isPresent()) {
            return jdbcTemplate.query(sql, Maker::makeFilm, count.get());
        } else {
            return jdbcTemplate.query(sql, Maker::makeFilm, 10);
        }
    }
}
