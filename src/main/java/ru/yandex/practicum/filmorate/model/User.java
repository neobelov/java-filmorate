package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.LoginValidation;
import ru.yandex.practicum.filmorate.exceptions.UserFriendException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User implements StorageObject {
    private Integer id;
    @Email @NotBlank @NotNull @NotEmpty private String email;
    @NotBlank @NotEmpty @NotNull @LoginValidation private String login;
    private String name;
    @PastOrPresent private LocalDate birthday;

    private Set<Integer> friends = new HashSet<>();

    @JsonCreator
    private User(Integer id, String email, String login, String name, LocalDate birthday) {
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
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            this.name = login;
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
}
