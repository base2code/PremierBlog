package de.base2code.blog.repositories.mongo;

import de.base2code.blog.model.User;
import de.base2code.blog.repositories.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

@Profile("mongo")
public interface UserRepositoryMongo extends MongoRepository<User, String>, UserRepository {
    @Query("{'email': ?0}")
    Optional<User> findByEmail(String email);

    @Query("{'username': ?0}")
    Optional<User> findByUsername(String username);

    @Query(value = "{'username': ?0}", exists = true)
    boolean existsByUsername(String username);

    @Query(value = "{'email': ?0}", exists = true)
    boolean existsByEmail(String email);
}
