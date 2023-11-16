package de.base2code.blog.controller;

import de.base2code.blog.dto.web.user.UserLoginDto;
import de.base2code.blog.dto.web.user.UserRegisterDto;
import de.base2code.blog.dto.web.user.UserTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Authentication", description = "Authentication API")
public class AuthenticationController {

    @Operation(summary = "Register a new user", description = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered a new user",
                    content = @Content(
                            schema = @Schema(implementation = UserTokenDto.class)
                    )),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody
            (description = "User to register", required = true,
                    content = @Content(schema = @Schema(implementation = UserRegisterDto.class, hidden = true)))
    @PostMapping(value = "/register",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void register(@RequestBody UserRegisterDto userRegisterDto) {
        throw new UnsupportedOperationException("Not implemented yet");
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
    public void login(@RequestParam String username, @RequestParam String password) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
