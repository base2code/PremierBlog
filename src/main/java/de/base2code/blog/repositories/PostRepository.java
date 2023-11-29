package de.base2code.blog.repositories;

import de.base2code.blog.dto.web.posts.ExternalPostDto;
import de.base2code.blog.model.Post;
import de.base2code.blog.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, String> {
    Post findPostById(String id);

    List<Post> findAllByAuthor(User user);
}
