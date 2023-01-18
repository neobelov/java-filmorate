package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotations.MinReleaseDateValidation;
import ru.yandex.practicum.filmorate.annotations.PositiveDurationValidation;
import ru.yandex.practicum.filmorate.exceptions.FilmLikeException;

import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

@Data
public class Film implements StorageObject {
    @JsonIgnore
    private final String mainDBName = "films";
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
    @NotNull (message = "Film duration is null")
    private Duration duration;
    private Set<Integer> usersWhoLiked;
    private Set<Genre> genres;
    private MPA mpa;

    @JsonCreator
    public Film(Integer id, String name, String description, LocalDate releaseDate, Duration duration, List<Genre> genres, MPA mpa, List<Integer> usersWhoLiked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (genres != null) {
            this.genres = new LinkedHashSet<>(genres);
        } else {
            this.genres = new LinkedHashSet<>();
        }
        this.mpa = mpa;
        if (usersWhoLiked != null) {
            this.usersWhoLiked = new LinkedHashSet<>(usersWhoLiked);
        } else {
            this.usersWhoLiked = new LinkedHashSet<>();
        }
    }

    @JsonProperty("duration")
    private long getDurationSecondsPrimitive() {
        return duration.getSeconds();
    }

    private Long getDurationSeconds() {
        if (duration != null) {
            return duration.getSeconds();
        } else {
            return null;
        }
    }

    private Integer getMPAid() {
        if (mpa != null) {
            return mpa.getId();
        } else {
            return null;
        }
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

    @Override
    public Map<String, Object> toMainTableMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("film_id", getId());
        map.put("name", getName());
        map.put("description", getDescription());
        map.put("release_date", getReleaseDate());
        map.put("duration", getDurationSeconds());
        map.put("MPA_rating_id", getMPAid());
        return map;
    }

    @Override
    public Map<String, List<StorageObject>> toRelatedTablesMap() {
        Map<String, List<StorageObject>> map = new LinkedHashMap<>();
        if (getGenres() != null && !getGenres().isEmpty()) {
            map.put("film_genres", new ArrayList<>(getGenres()));
        } else {
            map.put("film_genres", new ArrayList<>());
        }
        return map;
    }

}
