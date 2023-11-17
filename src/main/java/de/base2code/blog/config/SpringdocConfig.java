package de.base2code.blog.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerAuth",
        scheme = "bearer")
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "noAuth",
        scheme = "none")
public class SpringdocConfig {

    @Value("${allowed-origin}")
    private String allowedOrigin;

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI();

        openAPI.addServersItem(new io.swagger.v3.oas.models.servers.Server().url(allowedOrigin));

        return openAPI;
    }

}
