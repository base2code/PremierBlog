package de.base2code.blog.dto.web.posts;

import de.base2code.blog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExternalPostsDto {
    private List<ExternalPostDto> posts = new ArrayList<>();

    public ExternalPostsDto(List<Post> posts1) {
        for (Post post : posts1) {
            posts.add(post.convertToExternal());
        }
    }
}
