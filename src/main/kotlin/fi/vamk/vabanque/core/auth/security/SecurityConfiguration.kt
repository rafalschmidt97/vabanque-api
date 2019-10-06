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

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val tokenService: TokenService) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http.csrf().disable().authorizeRequests()
      .antMatchers(
        *AuthController.ignoredPathsInAuth,
        *SwaggerConfiguration.ignoredPathsInAuth
      ).permitAll()
      .anyRequest().authenticated()

    http.addFilter(AuthorizationFilter(authenticationManager(), tokenService))
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }
}
