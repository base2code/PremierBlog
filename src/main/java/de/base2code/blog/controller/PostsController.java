package de.base2code.blog.controller;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import de.base2code.blog.dto.web.posts.ExternalPostsDto;
import de.base2code.blog.dto.web.posts.PostCreateDto;
import de.base2code.blog.exception.posts.NotTheAutorException;
import de.base2code.blog.exception.posts.PostNotFoundException;
import de.base2code.blog.exception.posts.UserNotFoundException;
import de.base2code.blog.model.Post;
import de.base2code.blog.model.User;
import de.base2code.blog.repositories.PostRepository;
import de.base2code.blog.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Posts", description = "Posts API")
@AllArgsConstructor
public class PostsController {
    private UserRepository userRepository;
    private PostRepository postRepository;

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
    public ResponseEntity<ExternalPostDto> createPost(Principal principal, @RequestBody PostCreateDto postCreateDto) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        Post post = new Post(
                new Date(System.currentTimeMillis()),
                postCreateDto.getTitle(),
                postCreateDto.getContent(),
                user.get()
        );

        postRepository.save(post);

        return ResponseEntity.ok(post.convertToExternal());
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
    public ResponseEntity<ExternalPostDto> updatePost(@PathVariable String id,
                                                    @RequestBody PostCreateDto postCreateDto,
                                                    Principal principal) throws UserNotFoundException, NotTheAutorException, PostNotFoundException {

        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        if (!post.get().getAuthor().getId().equals(user.get().getId())) {
            throw new NotTheAutorException();
        }

        if (postCreateDto.getTitle() != null) {
            post.get().setTitle(postCreateDto.getTitle());
        }

        if (postCreateDto.getContent() != null) {
            post.get().setContent(postCreateDto.getContent());
        }

        post.get().setUpdatedAt(new Date(System.currentTimeMillis()));

        postRepository.save(post.get());

        return ResponseEntity.ok(post.get().convertToExternal());
    }

    @Operation(summary = "Get all posts", description = "Gets all posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found posts",
                    content = @Content(
                            schema = @Schema(implementation = ExternalPostsDto.class)
                    ))
    })
    @Parameters(value = {
            @Parameter(name = "reverse", description = "Reverse order", example = "false"),
            @Parameter(name = "justOwnPosts", description = "Return just the own Posts (must be authenticated)", example = "false")
    })
    @SecurityRequirement(name = "bearerAuth")
    @SecurityRequirement(name = "noAuth")
    @GetMapping(value = "/posts",
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalPostsDto> getPosts(
            Principal principal,
            @RequestParam(required = false, name = "reverse", defaultValue = "false") Boolean reverse,
            @RequestParam(required = false, name = "justOwnPosts", defaultValue = "false") Boolean justOwnPosts) throws UserNotFoundException {

        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isEmpty() && Boolean.TRUE.equals(justOwnPosts)) {
            throw new UserNotFoundException();
        }

        List<Post> postsFound;
        if (Boolean.TRUE.equals(justOwnPosts)) {
            postsFound = postRepository.findAllByAuthor(user.get());
        } else {
            postsFound = postRepository.findAll();
        }

        if (Boolean.TRUE.equals(reverse)) {
            postsFound.sort((o1, o2) -> o2.getPostedAt().compareTo(o1.getPostedAt()));
        } else {
            postsFound.sort(Comparator.comparing(Post::getPostedAt));
        }

        ExternalPostsDto externalPostsDto = new ExternalPostsDto(postsFound);

        return ResponseEntity.ok(externalPostsDto);
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
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExternalPostDto> getPost(
            @PathVariable String id,
            Principal principal
    ) throws PostNotFoundException {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        return ResponseEntity.ok(post.get().convertToExternal());
    }

    @Operation(summary = "Deletes a post", description = "Deletes a post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/posts/{id}")
    public void deletePost(@PathVariable String id, Principal principal) throws UserNotFoundException, PostNotFoundException, NotTheAutorException {
        Optional<User> user = userRepository.findByUsername(principal.getName());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        if (!post.get().getAuthor().getId().equals(user.get().getId())) {
            throw new NotTheAutorException();
        }

        postRepository.delete(post.get());
    }

}
