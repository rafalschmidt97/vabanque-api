package fi.vamk.vabanque.core.auth.security

import fi.vamk.vabanque.core.auth.AuthController
import fi.vamk.vabanque.core.auth.token.TokenService
import fi.vamk.vabanque.core.swagger.SwaggerConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val tokenService: TokenService) : WebSecurityConfigurerAdapter() {
  override fun configure(http: HttpSecurity) {
    http
      .cors().and()
      .csrf().disable()
      .authorizeRequests()
      .antMatchers(
        *AuthController.ignoredPathsInAuth,
        *SwaggerConfiguration.ignoredPathsInAuth,
        "/actuator/**",
        "/"
      ).permitAll()
      .anyRequest().authenticated()

    http.addFilter(AuthorizationFilter(authenticationManager(), tokenService))
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val configuration = CorsConfiguration()
    configuration.allowedOrigins = listOf("*")
    configuration.allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
    configuration.allowedHeaders = listOf(
      "Authorization",
      "Content-Type",
      "X-Requested-With",
      "Accept",
      "Origin",
      "Access-Control-Request-Method",
      "Access-Control-Request-Headers"
    )
    configuration.exposedHeaders = listOf(
      "Access-Control-Allow-Origin",
      "Access-Control-Allow-Credentials",
      "Authorization"
    )
    configuration.allowCredentials = true
    configuration.maxAge = 3600

    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", configuration)
    return source
  }
}
