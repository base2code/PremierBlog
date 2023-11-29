package de.base2code.blog.repositories.mongo;

import de.base2code.blog.model.Post;
import de.base2code.blog.model.User;
import de.base2code.blog.repositories.PostRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Profile("mongo")
public interface PostRepositoryMongo extends MongoRepository<Post, String>, PostRepository {
    @Query("{'id': ?0}")
    Post findPostById(String id);

    @Query("{'author': ?0}")
    List<Post> findAllByAuthor(User user);
}
