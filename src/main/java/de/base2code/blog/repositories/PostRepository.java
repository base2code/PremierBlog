package de.base2code.blog.repositories;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import de.base2code.blog.model.Post;
import de.base2code.blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    @Query("{'id': ?0}")
    Post findPostById(String id);

    @Query("{'author': ?0}")
    List<Post> findAllByAuthor(User user);
}
