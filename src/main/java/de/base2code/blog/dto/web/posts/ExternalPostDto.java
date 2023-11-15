package de.base2code.blog.dto.web.posts;

import de.base2code.blog.dto.web.user.PublicUserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExternalPostDto {
    private String id;
    private String title;
    private String content;
    private PublicUserDto author;
}
