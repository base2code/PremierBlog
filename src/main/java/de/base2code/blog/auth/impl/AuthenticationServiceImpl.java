package de.base2code.blog.auth.impl;

import de.base2code.blog.auth.AuthenticationService;
import de.base2code.blog.auth.JwtService;
import de.base2code.blog.dto.web.user.UserLoginDto;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.dto.web.user.UserTokenDto;
import de.base2code.blog.exception.login.InvalidUsernameOrPasswordException;
import de.base2code.blog.exception.register.EmailAlreadyTakenException;
import de.base2code.blog.exception.register.InvalidUsernameException;
import de.base2code.blog.exception.register.UsernameAlreadyTakenException;
import de.base2code.blog.model.User;
import de.base2code.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public UserTokenDto signup(UserRegisterDto request) throws InvalidUsernameException, UsernameAlreadyTakenException, EmailAlreadyTakenException {
        if (request.getUsername().contains("@")) {
            throw new InvalidUsernameException();
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyTakenException();
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyTakenException();
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = new User(request);
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return new UserTokenDto(jwt);
    }

    @Override
    public UserTokenDto signin(UserLoginDto request) throws InvalidUsernameOrPasswordException {
        User user;
        if (request.getUsername().contains("@")) {
             user = userRepository.findByEmail(request.getUsername())
                    .orElseThrow(InvalidUsernameOrPasswordException::new);
        } else {
                user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(InvalidUsernameOrPasswordException::new);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidUsernameOrPasswordException();
        }

        var jwt = jwtService.generateToken(user);
        return new UserTokenDto(jwt);
    }
}
