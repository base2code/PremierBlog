package de.base2code.blog.controller.posts;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
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

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatePostTests extends BasePostsTests {
    @Test
    void createPostWithValidUser() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");

        super.getMockMvc().perform(
                        post("/posts")
                                .header("Authorization", "Bearer " + super.getToken())
                                .content(super.getGson().toJson(postCreateDto))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(postCreateDto.getTitle()))
                .andExpect(jsonPath("$.content").value(postCreateDto.getContent()))
                .andExpect(jsonPath("$.author.username").value(super.getUsername1()))
                .andDo(result -> {
                    // Check if post exists
                    String content = result.getResponse().getContentAsString();
                    ExternalPostDto externalPostDto = super.getGson().fromJson(content, ExternalPostDto.class);
                    Post post = super.getPostRepository().findPostById(externalPostDto.getId());
                    if (post == null) {
                        throw new Exception("Post not found in database");
                    }
                })
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    ExternalPostDto externalPostDto = super.getGson().fromJson(content, ExternalPostDto.class);
                    super.getPostRepository().deleteById(externalPostDto.getId());
                });
    }

    @Test
    void createPostWithInvalidUser() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");

        super.getMockMvc().perform(
                        post("/posts")
                                .header("Authorization", "Bearer " + "asd")
                                .content(super.getGson().toJson(postCreateDto))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createPostAnonymous() throws Exception {
        PostCreateDto postCreateDto = new PostCreateDto("Test", "Content");

        super.getMockMvc().perform(
                        post("/posts")
                                .content(super.getGson().toJson(postCreateDto))
                                .contentType("application/json")
                ).andDo(print())
                .andExpect(status().isForbidden());
    }
}
