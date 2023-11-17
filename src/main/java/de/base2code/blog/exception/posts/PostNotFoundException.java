package de.base2code.blog.exception.posts;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Post not found", code = org.springframework.http.HttpStatus.NOT_FOUND)
public class PostNotFoundException extends Exception {
}
