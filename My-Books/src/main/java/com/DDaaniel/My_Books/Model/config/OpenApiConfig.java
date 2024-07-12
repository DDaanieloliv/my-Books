package com.DDaaniel.My_Books.Model.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Daniel",
                        email = "daniel****@gmail.com",
                        url = "https://github.com/DDaanieloliv"
                ),
                description = "(OpenApi documentation for Spring) API para gerenciar uma coleção de livros pessoais. ",
                title = "OpenApi specification - Daniel",
                version = "1.0",
                license = @License(
                        name = "MIT Licence",
                        url = "https://Some-url.com"
                ),
                termsOfService = "Terms of service(Url)"
        ),
        servers = {
                @Server(
                        description = "Local DEV ENV",
                        url = "https://github.com/DDaanieloliv"
                ),
                @Server(
                        description = "PROD DEV ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}
