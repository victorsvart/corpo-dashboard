package com.dashboard.api.infrastructure.security;

import com.dashboard.api.infrastructure.jwt.JwtFilter;
import com.dashboard.api.infrastructure.jwt.TokenProvider;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security configuration class for setting up Spring Security.
 *
 * <p>Configures CORS, disables CSRF and default login mechanisms, applies stateless session
 * management, and sets up JWT filter. Defines publicly accessible endpoints and secures others.
 */
@Configuration
public class SecurityConfiguration {

  private final TokenProvider tokenProvider;

  /**
   * Constructs the security configuration with a TokenProvider.
   *
   * @param tokenProvider the JWT token provider for authentication filtering
   */
  public SecurityConfiguration(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * Defines CORS configuration allowing frontend access from localhost.
   *
   * @return a CorsConfigurationSource with allowed origins, methods, headers, and credentials
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:5173", "http://127.0.0.1:5173"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    config.setMaxAge(3600L);
    config.setExposedHeaders(List.of("Set-Cookie", "Authorization"));
    config.setAllowPrivateNetwork(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  /**
   * Configures the main security filter chain.
   *
   * <p>Disables CSRF, form login, and HTTP basic auth. Enables CORS and stateless session
   * management. Adds JWT filter before username-password authentication filter. Permits
   * unauthenticated access to login, register, and Swagger docs endpoints. Secures all other
   * requests.
   *
   * @param http the HttpSecurity to configure
   * @return the configured SecurityFilterChain
   * @throws Exception if a configuration error occurs
   */
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(Customizer.withDefaults())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
        .formLogin(form -> form.disable())
        .httpBasic(basic -> basic.disable())
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers("/user/login")
                    .permitAll()
                    .requestMatchers("/user/register")
                    .permitAll()
                    .requestMatchers(
                        "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/apidocs.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated());

    return http.build();
  }

  /**
   * Password encoder bean that uses BCrypt hashing algorithm.
   *
   * @return a PasswordEncoder instance
   */
  @Bean
  public PasswordEncoder defaultPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Exposes the AuthenticationManager bean for use in authentication workflows.
   *
   * @param config the AuthenticationConfiguration
   * @return the AuthenticationManager instance
   * @throws Exception if unable to get the AuthenticationManager
   */
  @Bean
  public AuthenticationManager defaultAuthenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
