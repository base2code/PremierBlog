package de.base2code.blog.exception.register;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Email invalid", code = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends Exception {
}
