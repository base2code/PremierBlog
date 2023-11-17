package de.base2code.blog.exception.posts;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Your are not the author", code = org.springframework.http.HttpStatus.UNAUTHORIZED)
public class NotTheAutorException extends Exception {
}
