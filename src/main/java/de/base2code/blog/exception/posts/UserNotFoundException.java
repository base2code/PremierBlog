package de.base2code.blog.exception.posts;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "User not found", code = org.springframework.http.HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {
}
