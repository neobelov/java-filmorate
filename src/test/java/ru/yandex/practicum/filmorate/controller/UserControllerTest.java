package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addAndGetUser() throws Exception {
        User user = new User(null,"name@gmail.com","login","name",LocalDate.of(1998,1,18),null);
        Integer id = objectMapper.readValue(mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("name@gmail.com"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.birthday").value("1998-01-18"))
                .andReturn().getResponse().getContentAsString(), User.class).getId();
        user.setId(id);
        MvcResult result = mockMvc.perform(get("/users")).andReturn();
        result.getResponse().setCharacterEncoding("utf-8");
        String resultContent = result.getResponse().getContentAsString();
        List<User> list = objectMapper.readValue(resultContent, new TypeReference<List<User>>(){});
        User user2 = list.get(id-1);
        assertEquals(user, user2);
    }

    @Test
    void addEmptyUser() throws Exception {
        mockMvc.perform(
                        post("/users")
                                .content("")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserEmailFail() throws Exception {
        User user = new User(null,"name@gmail....com","login","name",LocalDate.of(1998,1,18),null);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserLoginFail() throws Exception {
        User user = new User(null,"name@gmail.com","","name",LocalDate.of(1998,1,18),null);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserNameIsEmpty() throws Exception {
        User user = new User(null,"name1@gmail.com","login1","", LocalDate.of(1998,1,18),null);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("login1"));
    }

    @Test
    void addUserBirthdayFail() throws Exception {
        User user = new User(null,"name@gmail.com","login","name", LocalDate.of(2200,1,18),null);
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

}
