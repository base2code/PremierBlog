package de.base2code.blog.dto.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginDto {
    private String username;
    private String password;
}
