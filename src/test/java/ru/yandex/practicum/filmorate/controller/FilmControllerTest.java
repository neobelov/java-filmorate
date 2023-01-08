package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addAndGetFilm() throws Exception {
        Film film = new Film("name","description", LocalDate.of(2022,12,17), Duration.ofSeconds(100));
        Integer id = objectMapper.readValue(mockMvc.perform(
                post("/films")
                .content(objectMapper.writeValueAsString(film))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.releaseDate").value("2022-12-17"))
                .andExpect(jsonPath("$.duration").value("100"))
                .andReturn().getResponse().getContentAsString(), Film.class).getId();
        film.setId(id);
        MvcResult result = mockMvc.perform(get("/films")).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        List<Film> list = objectMapper.readValue(resultContent, new TypeReference<List<Film>>(){});
        Film film2 = list.get(id-1);
        assertEquals(film, film2);
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
        Film film = new Film("", "description", LocalDate.of(2022, 12, 17), Duration.ofSeconds(100));
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    void addFilmDescriptionFail() throws Exception {
        Film film = new Film("name","",LocalDate.of(2022,12,17),Duration.ofSeconds(100));
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFilmReleaseDateFail() throws Exception {
        Film film = new Film("name","description",LocalDate.of(1700,12,17),Duration.ofSeconds(100));
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addFilmDurationFail() throws Exception {

        Film film2 = new Film("name","description",LocalDate.of(2022,12,17),Duration.ofSeconds(-100));
                    mockMvc.perform(post("/films")
                                    .content(objectMapper.writeValueAsString(film2))
                                    .contentType(MediaType.APPLICATION_JSON)
                            )
                            .andExpect(status().isBadRequest());
    }
}
