package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
public class User {
    private Integer id;
    @Email @NotBlank @NotNull @NotEmpty private String email;
    @NotBlank @NotEmpty @NotNull private String login;
    private String name;
    @PastOrPresent private LocalDate birthday;

    private User() {}

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public boolean isValid() {
        if (login.contains(" ")) {
            throw new ValidationException("Login should not contain spaces");
        }
        if (name != null && !name.isEmpty() && !name.isBlank()) {
            this.name = name;
        } else {
            this.name = login;
        }
        return true;
    }
}
