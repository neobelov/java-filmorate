package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addAndGetFilm() throws Exception {
        Film film = new Film(null,"Имя","Описание", LocalDate.of(2022,12,17), Duration.ofSeconds(100), List.of(new Genre(2,"Драма")), null,null);
        Integer id = objectMapper.readValue(mockMvc.perform(
                post("/films")
                .content(objectMapper.writeValueAsString(film))
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Имя"))
                .andExpect(jsonPath("$.description").value("Описание"))
                .andExpect(jsonPath("$.releaseDate").value("2022-12-17"))
                .andExpect(jsonPath("$.duration").value("100"))
                .andReturn().getResponse().getContentAsString(), Film.class).getId();
        film.setId(id);
        MvcResult result = mockMvc.perform(get("/films")).andReturn();
        result.getResponse().setCharacterEncoding("utf-8");
        String resultContent = result.getResponse().getContentAsString();
        List<Film> list = objectMapper.readValue(resultContent, new TypeReference<List<Film>>(){});
        Film film2 = list.get(id-1);
        assertEquals(film, film2);
        Film film3 = new Film(1,"Имя","Описание", LocalDate.of(2022,12,17), Duration.ofSeconds(100), List.of(), null,null);
        MvcResult result4 = mockMvc.perform(
                        put("/films")
                                .content(objectMapper.writeValueAsString(film3))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Имя"))
                .andExpect(jsonPath("$.description").value("Описание"))
                .andExpect(jsonPath("$.releaseDate").value("2022-12-17"))
                .andExpect(jsonPath("$.duration").value("100"))
                .andReturn();
        result4.getResponse().setCharacterEncoding("utf-8");
        Film film4 = objectMapper.readValue(result4.getResponse().getContentAsString(), Film.class);
        assertEquals(film3, film4);
    }

    @Test
    void addEmptyFilm() throws Exception {
        mockMvc.perform(
                        post("/films")
                                .content("")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFilmNameFail() throws Exception {
        Film film = new Film(null,"", "description", LocalDate.of(2022, 12, 17), Duration.ofSeconds(100), null, null,null);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }


    void addFilmDescriptionFail() throws Exception {
        Film film = new Film(null,"name","",LocalDate.of(2022,12,17),Duration.ofSeconds(100), null, null,null);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFilmReleaseDateFail() throws Exception {
        Film film = new Film(null,"name","description",LocalDate.of(1700,12,17),Duration.ofSeconds(100), null, null,null);
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFilmDurationFail() throws Exception {
        Film film2 = new Film(null,"name","description",LocalDate.of(2022,12,17),Duration.ofSeconds(-100), null, null,null);
                    mockMvc.perform(post("/films")
                                    .content(objectMapper.writeValueAsString(film2))
                                    .contentType(MediaType.APPLICATION_JSON)
                            )
                            .andExpect(status().isBadRequest());
    }
}
