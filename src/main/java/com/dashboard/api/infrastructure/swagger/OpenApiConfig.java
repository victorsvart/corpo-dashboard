package com.dashboard.api.infrastructure.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing OpenAPI (Swagger) documentation.
 *
 * <p>Sets API metadata and configures JWT Bearer authentication scheme for secured endpoints in the
 * API documentation.
 */
@Configuration
public class OpenApiConfig {

  /**
   * Creates a custom OpenAPI bean with metadata and security scheme.
   *
   * @return an OpenAPI instance configured with API info and security settings
   */
  @Bean
  public OpenAPI customOpenApi() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
        .info(
            new Info().title("Dashboard API").version("1.0.0").description("Dashboard Project API"))
        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}
