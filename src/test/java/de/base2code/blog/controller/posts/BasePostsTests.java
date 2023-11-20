package de.base2code.blog.controller.posts;

import com.google.gson.Gson;
import de.base2code.blog.auth.AuthenticationService;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.exception.register.EmailAlreadyTakenException;
import de.base2code.blog.exception.register.InvalidEmailException;
import de.base2code.blog.exception.register.InvalidUsernameException;
import de.base2code.blog.exception.register.UsernameAlreadyTakenException;
import de.base2code.blog.repositories.PostRepository;
import de.base2code.blog.repositories.UserRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.security.SecureRandom;

@Getter
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BasePostsTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PostRepository postRepository;

    private final String username1 = "testuser-" + new SecureRandom().nextInt(1000) + 1;
    private final String email1 = "test-" + username1 + "@example.com";
    private final String password1 = "password" + new SecureRandom().nextInt(10000000) + 1;

    private final String username2 = "testuser-" + new SecureRandom().nextInt(10000000) + 1;
    private final String email2 = "test-" + username2 + "@example.com";
    private final String password2 = "password" + new SecureRandom().nextInt(10000000) + 1;

    private String token;
    private String token2;

    private final Gson gson = new Gson();

    @BeforeAll
    void beforeAll() throws EmailAlreadyTakenException, InvalidUsernameException, UsernameAlreadyTakenException, InvalidEmailException {
        token = authenticationService.signup(new UserRegisterDto(username1, email1, password1)).getToken();
        token2 = authenticationService.signup(new UserRegisterDto(username2, email2, password2)).getToken();
    }

    @AfterAll
    void deletion() {
        userRepository.findByUsername(username1).ifPresent(value -> userRepository.delete(value));
        userRepository.findByUsername(username2).ifPresent(value -> userRepository.delete(value));
    }
}
