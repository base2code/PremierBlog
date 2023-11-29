package de.base2code.blog.model;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    private Date postedAt;
    private Date updatedAt;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
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
