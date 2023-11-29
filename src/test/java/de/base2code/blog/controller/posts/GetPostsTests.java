package de.base2code.blog.controller.posts;

import de.base2code.blog.dto.web.posts.ExternalPostsDto;
import de.base2code.blog.dto.web.posts.PostCreateDto;
import de.base2code.blog.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetPostsTests extends BasePostsTests {
    Post createPost() {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername1()).orElseThrow()
        );
        super.getPostRepository().save(post);
        return post;
    }

    @Test
    void getPostsWithUser() throws Exception {
        super.getMockMvc().perform(
                        get("/posts")
                                .header("Authorization", "Bearer " + super.getToken())
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").exists());
    }

    @Test
    void getJustOwnPostsWithUser() throws Exception {
        Post post = createPost();

        super.getMockMvc().perform(
                        get("/posts")
                                .header("Authorization", "Bearer " + super.getToken())
                                .param("justOwnPosts", "true")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").exists())
                .andExpect(jsonPath("$.posts.length()").value(1))
                .andExpect(jsonPath("$.posts[0].id").value(post.getId()))
                .andExpect(jsonPath("$.posts[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$.posts[0].content").value(post.getContent()))
                .andExpect(jsonPath("$.posts[0].author.username").value(super.getUsername1()))
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }

    @Test
    void getJustOwnPostsAnonymous() throws Exception {
        super.getMockMvc().perform(
                        get("/posts")
                                .param("justOwnPosts", "true")
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPostsReversedAuthenticated() throws Exception {
        Post post1 = createPost();
        Thread.sleep(1100);
        Post post2 = createPost();

        super.getMockMvc().perform(
                get("/posts")
                        .header("Authorization", "Bearer " + super.getToken())
                        .param("reverse", "true")
        ).andDo(print())
                .andExpect(status().isOk())
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    ExternalPostsDto externalPostsDto = super.getGson().fromJson(content, ExternalPostsDto.class);
                    System.out.println(externalPostsDto);
                    if (!externalPostsDto.getPosts().get(0).getId().equals(post2.getId())) {
                        throw new Exception("Posts not reversed");
                    }
                });

        getPostRepository().delete(post1);
        getPostRepository().delete(post2);
        Assertions.assertTrue(true);
    }

    @Test
    void getPostsAnonymous() throws Exception {
            super.getMockMvc().perform(
                            get("/posts")
                    ).andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.posts").exists());
    }
}
