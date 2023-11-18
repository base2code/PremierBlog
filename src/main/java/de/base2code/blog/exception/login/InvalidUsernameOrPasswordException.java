package de.base2code.blog.exception.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid username or password", code = HttpStatus.BAD_REQUEST)
public class InvalidUsernameOrPasswordException extends Exception {
}
