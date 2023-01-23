package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Data
public class Friend implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "user_friends";
    private Integer id;
    private Integer userId;
    private Integer friendId;

    public Friend(Integer whoAddsId, Integer whoToAddId) {
        this.friendId = whoToAddId;
        this.userId = whoAddsId;
    }

    public Friend (Integer id, Integer whoAddsId, Integer whoToAddId) {
        this(whoAddsId,whoToAddId);
        this.id = id;
    }

    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", getId());
        map.put("user_id", getUserId());
        map.put("friend_id", getFriendId());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        return null;
    }
}
