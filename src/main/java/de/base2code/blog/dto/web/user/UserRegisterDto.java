package de.base2code.blog.dto.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;
}
