package de.base2code.blog.dto.web.posts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExternalPostsDto {
    private List<ExternalPostDto> posts;
}
