package ru.yandex.practicum.filmorate.utilities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
public class Constants {
    public static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895,12,28);
}
