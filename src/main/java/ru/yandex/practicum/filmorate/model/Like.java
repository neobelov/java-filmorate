package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class Like implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "film_likes";

    private Integer id;
    private Integer filmId;
    private Integer userWhoLikedId;

    public Like(Integer id, Integer filmId, Integer userWhoLikedId) {
        this(filmId, userWhoLikedId);
        this.id = id;
    }

    public Like(Integer filmId, Integer userWhoLikedId) {
        this.filmId = filmId;
        this.userWhoLikedId = userWhoLikedId;
    }

    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", getId());
        map.put("film_id", getFilmId());
        map.put("user_who_liked_id", getUserWhoLikedId());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        return null;
    }
}
