package de.base2code.blog.repositories.sql;

import de.base2code.blog.model.Post;
import de.base2code.blog.repositories.PostRepository;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Profile("sql")
@PersistenceContext
public interface PostRepositorySQL extends JpaRepository<Post, String>, PostRepository {

}
