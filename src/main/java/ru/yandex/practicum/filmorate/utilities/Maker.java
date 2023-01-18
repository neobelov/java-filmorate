package ru.yandex.practicum.filmorate.utilities;

import ru.yandex.practicum.filmorate.exceptions.ResourceNotFoundException;
import ru.yandex.practicum.filmorate.model.*;

import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Maker {
    static SqlArrayConverter converter = new SqlArrayConverter();
    public <T> T make(ResultSet rs, int rowNum, Class<T> type) throws SQLException {
        if (Film.class.equals(type)) {
            return type.cast(makeFilm(rs,rowNum));
        } else if (User.class.equals(type)) {
            return type.cast(makeUser(rs,rowNum));
        } else if (Genre.class.equals(type)) {
            return type.cast(makeGenre(rs,rowNum));
        } else if (MPA.class.equals(type)) {
            return type.cast(makeMPA(rs,rowNum));
        } else {
            return null;
        }
    }
    public static Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        MPA mpa = new MPA(rs.getInt("MPA_rating_id"), rs.getString("MPA_rating_name"));
        if (mpa.getId() == 0) {mpa = null;}
        return new Film(
                rs.getInt("film_id"),
                new String(rs.getString("name").getBytes(),StandardCharsets.UTF_8),
                rs.getString("description"),
                rs.getDate("release_date").toLocalDate(),
                Duration.ofSeconds(rs.getInt("duration")),
                makeGenresFromArrays(
                        converter.convert(rs.getArray("genre_ids")),
                        converter.convert(rs.getArray("genre_names"))),
                mpa,
                converter.convert(rs.getArray("user_who_liked_id"))
        );
    }
    public static User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate(),
                converter.convert(rs.getArray("friend_ids"))
        );
    }
    public static Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getInt("genre_id"),
                rs.getString("name")
        );
    }
    public static MPA makeMPA(ResultSet rs, int rowNum) throws SQLException {
        return new MPA(
                rs.getInt("MPA_rating_id"),
                rs.getString("name")
        );
    }

    public static Like makeLike(ResultSet rs, int rowNum) throws SQLException {
        return new Like(
                rs.getInt("id"),
                rs.getInt("film_id"),
                rs.getInt("user_who_liked_id")
        );
    }

    public static Friend makeFriend(ResultSet rs, int rowNum) throws SQLException {
        return new Friend(
                rs.getInt("id"),
                rs.getInt("friend_id"),
                rs.getInt("user_id")
        );
    }


    private static List<Genre> makeGenresFromArrays(List<Integer> ids, List<String> names) {
        if (ids != null && names != null) {
            List<Genre> genres = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                genres.add(new Genre(ids.get(i), names.get(i)));
            }
            return genres;
        } else {
            return null;
        }
    }
}
