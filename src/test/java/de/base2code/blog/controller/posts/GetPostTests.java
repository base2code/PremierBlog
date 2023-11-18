package de.base2code.blog.controller.posts;

import de.base2code.blog.dto.web.posts.PostCreateDto;
import de.base2code.blog.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetPostTests extends BasePostsTests {

    @Test
    void getPostWithValidUser() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername1()).orElseThrow()
        );
        super.getPostRepository().save(post);

        super.getMockMvc().perform(
                        get("/posts/" + post.getId())
                                .header("Authorization", "Bearer " + super.getToken())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(post.getContent()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.author.username").value(super.getUsername1()))
                .andDo(
                        result -> {
                            Post post1 = super.getPostRepository().findPostById(post.getId());
                            if (post1 == null) {
                                throw new Exception("Post not found in database");
                            }
                        }
                )
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }

    @Test
    void getPostAnonymous() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername1()).orElseThrow()
        );
        super.getPostRepository().save(post);

        super.getMockMvc().perform(
                        get("/posts/" + post.getId())
                ).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }
}
