package de.base2code.blog.dto.web.posts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDto {
    private String title;
    private String content;
}
