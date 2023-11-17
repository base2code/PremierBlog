package de.base2code.blog.auth;

import de.base2code.blog.dto.web.user.UserLoginDto;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.dto.web.user.UserTokenDto;
import de.base2code.blog.exception.login.InvalidUsernameOrPasswordException;
import de.base2code.blog.exception.register.EmailAlreadyTakenException;
import de.base2code.blog.exception.register.InvalidUsernameException;
import de.base2code.blog.exception.register.UsernameAlreadyTakenException;

public interface AuthenticationService {
    UserTokenDto signup(UserRegisterDto request) throws InvalidUsernameException, UsernameAlreadyTakenException, EmailAlreadyTakenException;

    UserTokenDto signin(UserLoginDto request) throws InvalidUsernameOrPasswordException;
}