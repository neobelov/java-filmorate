package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.MinReleaseDateValidation;
import ru.yandex.practicum.filmorate.annotations.PositiveDurationValidation;
import ru.yandex.practicum.filmorate.exceptions.FilmLikeException;

import javax.validation.constraints.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film implements StorageObject {
    private Integer id;
    @NotNull(message = "Film name is null")
    @NotBlank(message = "Film name is blank")
    @NotEmpty(message = "Film name is empty")
    private String name;
    @Size(max = 200, message = "Film description is longer than 200 symbols")
    private String description;
    @MinReleaseDateValidation(message = "Release date of film is before 28 December 1895")
    private LocalDate releaseDate;
    @PositiveDurationValidation(message = "Film duration is negative")
    private Duration duration;

    private Set<Integer> usersWhoLiked = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @JsonCreator
    private Film(Integer id, String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @JsonProperty("duration")
    public long getDurationSeconds() {
        return duration.getSeconds();
    }

    public Film addLike(Integer userId) {
        if (usersWhoLiked.contains(userId)) {
            throw new FilmLikeException("Film with id " + id + " is already liked by User with id " + userId);
        }
        usersWhoLiked.add(userId);
        return this;
    }

    public Film deleteLike(Integer userId) {
        if (!usersWhoLiked.contains(userId)) {
            throw new FilmLikeException("Film with id " + id + " is not liked by User with id " + userId);
        }
        usersWhoLiked.remove(userId);
        return this;
    }
}
