package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class Genre implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "genres";
    private Integer id;
    private String name;

    @JsonCreator
    public Genre(Integer id, String name) {
        this(id);
        this.name = name;
    }

    public Genre (Integer id) {
        this.id = id;
    }
    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("genre_id", getId());
        map.put("name", getName());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        return null;
    }
}
