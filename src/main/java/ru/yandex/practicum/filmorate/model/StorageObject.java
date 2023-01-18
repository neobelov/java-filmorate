package ru.yandex.practicum.filmorate.model;

import java.util.List;
import java.util.Map;

public interface StorageObject {
    Integer getId();
    void setId(Integer id);
    Map<String, Object> toMainTableMap(); // must return all single-entity fields (exclude Lists, Maps, etc)
    Map<String, List<StorageObject>> toRelatedTablesMap(); //must return all related StorageObject Lists
    String getMainDBName();
}
