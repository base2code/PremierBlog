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
class UpdatePostTests extends BasePostsTests {

    @Test
    void updatePost() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername1()).orElseThrow()
        );
        super.getPostRepository().save(post);

        PostCreateDto postCreateDto1 = new PostCreateDto("Test1", "Content1");

        super.getMockMvc().perform(
                        patch("/posts/" + post.getId())
                                .header("Authorization", "Bearer " + super.getToken())
                                .content(super.getGson().toJson(postCreateDto1))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(postCreateDto1.getContent()))
                .andExpect(jsonPath("$.title").value(postCreateDto1.getTitle()))
                .andExpect(jsonPath("$.author.username").value(super.getUsername1()))
                .andDo(result -> {
                    Post post1 = super.getPostRepository().findPostById(post.getId());
                    if (post1 == null) {
                        throw new Exception("Post not found in database");
                    }
                })
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }

    @Test
    void updatePostAnonymous() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername1()).orElseThrow()
        );
        super.getPostRepository().save(post);

        PostCreateDto postCreateDto1 = new PostCreateDto("Test1", "Content1");

        super.getMockMvc().perform(
                        patch("/posts/" + post.getId())
                                .content(super.getGson().toJson(postCreateDto1))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(result -> {
                    Post post1 = super.getPostRepository().findPostById(post.getId());
                    if (post1 == null) {
                        throw new Exception("Post not found in database");
                    }
                    if (postCreateDto1.getTitle().equals(post.getTitle()) || postCreateDto1.getContent().equals(post.getContent())) {
                        throw new Exception("Post was changed (but should not be)");
                    }
                })
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }

    @Test
    void updatePostWithOtherUser() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");
        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                super.getUserRepository().findByUsername(super.getUsername2()).orElseThrow()
        );
        super.getPostRepository().save(post);

        PostCreateDto postCreateDto1 = new PostCreateDto("Test1", "Content1");

        super.getMockMvc().perform(
                        patch("/posts/" + post.getId())
                                .content(super.getGson().toJson(postCreateDto1))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isNotFound())
                .andDo(result -> {
                    Post post1 = super.getPostRepository().findPostById(post.getId());
                    if (post1 == null) {
                        throw new Exception("Post not found in database");
                    }
                    if (postCreateDto1.getTitle().equals(post.getTitle()) || postCreateDto1.getContent().equals(post.getContent())) {
                        throw new Exception("Post was changed (but should not be)");
                    }
                })
                .andDo(
                        result -> super.getPostRepository().deleteById(post.getId())
                );
    }
}
