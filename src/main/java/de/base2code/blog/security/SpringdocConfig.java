package de.base2code.blog.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
public class SpringdocConfig {}
