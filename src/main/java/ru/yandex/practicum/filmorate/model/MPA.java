package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class MPA implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "MPA_ratings";
    private Integer id;
    private String name;
    public MPA(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("MPA_rating_id", getId());
        map.put("name", getName());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        return null;
    }
}
