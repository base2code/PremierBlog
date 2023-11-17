package de.base2code.blog.model;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
public class Post {
    @Id
    private String id;
    private Date postedAt;
    private Date updatedAt;
    private String title;
    private String content;
    private User author;

    public Post(Date postedAt, String title, String content, User author) {
        this.postedAt = postedAt;
        this.updatedAt = postedAt;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post() {
    }

    public ExternalPostDto convertToExternal() {
        return new ExternalPostDto(
            this.id,
            this.postedAt,
            this.updatedAt,
            this.title,
            this.content,
            this.author.convertToPublic()
        );
    }
}
