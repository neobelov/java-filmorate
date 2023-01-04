package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

@Data
public class Film {
    private Integer id;
    @NotNull @NotBlank @NotEmpty private String name;
    @Size (max = 200) private String description;
    private LocalDate releaseDate;
    private Duration duration;

    private Film() {}
    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public boolean isValid() {
        if (getReleaseDate().isBefore(LocalDate.of(1895, 12,28))) {
            throw new ValidationException("Release date is before 28 December 1895");
        }
        if (getDuration().isNegative()) {
            throw new ValidationException("Duration is negative");
        }
        return true;
    }

    @JsonProperty("duration")
    public long getDurationSeconds() {
        return duration.getSeconds();
    }

}
