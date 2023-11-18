package de.base2code.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.SecureRandom;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final String username = "testuser-" + new SecureRandom().nextInt(1000) + 1;
    private final String email = "test-" + username + "@example.com";
    private final String password = "password" + new SecureRandom().nextInt(1000) + 1;

    @Test
    @Order(1)
    void registerValid() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto(username, email, password);
        Gson gson = new Gson();
        String json = gson.toJson(userRegisterDto);

        this.mockMvc.perform(
                post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(
                print()
        ).andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
    }

    @Test
    @Order(2)
    void loginValid() throws Exception {
        this.mockMvc.perform(
                get("/login")
                        .param("username", username)
                        .param("password", password)
        ).andDo(
                print()
        ).andExpect(status().isOk()).andExpect(jsonPath("$.token").exists());
    }

    @Test
    @Order(3)
    void registerDuplicateUsername() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto(username, email + "1", password);
        Gson gson = new Gson();
        String json = gson.toJson(userRegisterDto);

        this.mockMvc.perform(
                post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(
                print()
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(4)
    void registerDuplicateEmail() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto(username + "1", email, password);
        Gson gson = new Gson();
        String json = gson.toJson(userRegisterDto);

        this.mockMvc.perform(
                post("/register")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andDo(
                print()
        ).andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void loginInvalidUsername() throws Exception {
        this.mockMvc.perform(
                get("/login")
                        .param("username", username + "1")
                        .param("password", password)
        ).andDo(
                print()
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void loginInvalidPassword() throws Exception {
        this.mockMvc.perform(
                get("/login")
                        .param("username", username)
                        .param("password", password + "1")
        ).andDo(
                print()
        ).andExpect(status().isBadRequest());
    }

    @AfterAll
    void deletion() {
        Optional<de.base2code.blog.model.User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> userRepository.delete(value));

        Assertions.assertFalse(userRepository.findByUsername(username).isPresent());
    }
}
