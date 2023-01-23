package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.LoginValidation;
import ru.yandex.practicum.filmorate.exceptions.UserFriendException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
public class User implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "users";
    private Integer id;
    @Email @NotBlank @NotNull @NotEmpty private String email;
    @NotBlank @NotEmpty @NotNull @LoginValidation private String login;
    private String name;
    @PastOrPresent private LocalDate birthday;

    private Set<Integer> friends;

    @JsonCreator
    public User(Integer id, String email, String login, String name, LocalDate birthday, List<Integer> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            this.name = login;
        }
        if (friends != null) {
            this.friends = new LinkedHashSet<>(friends);
        } else {
            this.friends = new LinkedHashSet<>();
        }
    }


    public User addFriend (Integer userId) {
        if (friends.contains(userId)) {
            throw new UserFriendException(String.format("User with id %d already has a friend with id %d", id, userId));
        }
        friends.add(userId);
        return this;
    }

    public User deleteFriend(Integer userId) {
        if (!friends.contains(userId)) {
            throw new UserFriendException(String.format("User with id %d doesn't have a friend with id %d", id, userId));
        }
        friends.remove(userId);
        return this;
    }
    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("user_id", getId());
        map.put("email", getEmail());
        map.put("login", getLogin());
        map.put("name", getName());
        map.put("birthday", getBirthday());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        return null;
    }

}
