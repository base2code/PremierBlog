package de.base2code.blog.exception.register;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Username already taken", code = org.springframework.http.HttpStatus.CONFLICT)
public class UsernameAlreadyTakenException extends Exception {
}
