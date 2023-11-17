package de.base2code.blog.exception.login;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid username or password", code = org.springframework.http.HttpStatus.UNAUTHORIZED)
public class InvalidUsernameOrPasswordException extends Exception {
}
