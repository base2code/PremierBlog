package de.base2code.blog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "PremierBlog API", version = "v1", description = "PremierBlog API"))
@SpringBootApplication
public class PremierApplicationBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PremierApplicationBackendApplication.class, args);
    }

}
