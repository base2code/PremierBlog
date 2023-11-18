package de.base2code.blog.controller;

import de.base2code.blog.auth.AuthenticationService;
import de.base2code.blog.dto.web.TextDto;
import de.base2code.blog.dto.web.user.UserLoginDto;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.dto.web.user.UserTokenDto;
import de.base2code.blog.exception.login.InvalidUsernameOrPasswordException;
import de.base2code.blog.exception.register.EmailAlreadyTakenException;
import de.base2code.blog.exception.register.InvalidEmailException;
import de.base2code.blog.exception.register.InvalidUsernameException;
import de.base2code.blog.exception.register.UsernameAlreadyTakenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered a new user",
                    content = @Content(
                            schema = @Schema(implementation = UserTokenDto.class)
                    )),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "400", description = "Invalid username",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "User to register", required = true,
                    content = @Content(schema = @Schema(implementation = UserRegisterDto.class, hidden = true)))
    @PostMapping(value = "/register",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenDto> register(@RequestBody UserRegisterDto userRegisterDto) throws EmailAlreadyTakenException, InvalidUsernameException, UsernameAlreadyTakenException, InvalidEmailException {
        return ResponseEntity.ok(authenticationService.signup(userRegisterDto));
    }



    @Operation(summary = "Login", description = "Login an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Credentials ok",
                    content = @Content(
                            schema = @Schema(implementation = UserTokenDto.class)
                    )),
            @ApiResponse(responseCode = "400", description = "Invalid credentials",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @Parameters(value = {
            @Parameter(name = "username", description = "Username", required = true, example = "admin"),
            @Parameter(name = "password", description = "Password", required = true, example = "password")
    })
    @GetMapping(value = "/login",
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserTokenDto> login(@RequestParam String username, @RequestParam String password) throws InvalidUsernameOrPasswordException {
        UserLoginDto userLoginDto = new UserLoginDto(username, password);
        return ResponseEntity.ok(authenticationService.signin(userLoginDto));
    }

}
