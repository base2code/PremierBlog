package de.base2code.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.security.SecureRandom;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto("testuser", "test@example.com", "password");
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
}
