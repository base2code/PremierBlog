package de.base2code.blog.dto.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicUserDto {
    private String id;
    private String username;
}
