package de.base2code.blog.controller;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import de.base2code.blog.dto.web.posts.ExternalPostsDto;
import de.base2code.blog.dto.web.posts.PostCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.security.Principal;

@RestController
@Tag(name = "Posts", description = "Posts API")
public class PostsController {

    @Operation(summary = "Create post", description = "Creates a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created post",
                    content = @Content(
                            schema = @Schema(implementation = ExternalPostDto.class)
                    ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "Post to create", required = true,
                    content = @Content(schema = @Schema(implementation = PostCreateDto.class, hidden = true)))
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/posts",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse<ExternalPostDto> createPost(Principal principal, @RequestBody PostCreateDto postCreateDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    @Operation(summary = "Update Post", description = "Updates an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated post",
                    content = @Content(
                            schema = @Schema(implementation = ExternalPostDto.class)
                    ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "Post data to update", required = true,
                    content = @Content(schema = @Schema(implementation = PostCreateDto.class, hidden = true)))
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(value = "/posts/{id}",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse<ExternalPostDto> updatePost(@PathVariable String id,
                                                    @RequestBody PostCreateDto postCreateDto,
                                                    Principal principal) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Operation(summary = "Get all posts", description = "Gets all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found posts",
                    content = @Content(
                            schema = @Schema(implementation = ExternalPostsDto.class)
                    ))
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/posts",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse<ExternalPostsDto> getPosts(
            Principal principal,
            @RequestParam(required = false, name = "reverse", defaultValue = "false") Boolean reverse) {
        throw new UnsupportedOperationException("Not implemented yet");
    }



    @Operation(summary = "Get post", description = "Get a post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found post",
                    content = @Content(
                            schema = @Schema(implementation = ExternalPostDto.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/posts/{id}",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public HttpResponse<ExternalPostDto> getPost(
            @PathVariable String id,
            Principal principal
    ) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Operation(summary = "Deletes a post", description = "Deletes a post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @DeleteMapping(value = "/posts/{id}",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void deletePost(@PathVariable String id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
