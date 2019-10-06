package fi.vamk.vabanque.core.auth.security

import fi.vamk.vabanque.core.auth.token.TokenService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class AuthorizationFilter(
  authenticationManager: AuthenticationManager,
  private val tokenService: TokenService
) : BasicAuthenticationFilter(authenticationManager) {

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    val header = request.getHeader("Authorization")

    if (header != null && header.startsWith("Bearer")) {
      val auth = getAuthentication(header)
      SecurityContextHolder.getContext().authentication = auth
    }

    chain.doFilter(request, response)
  }

  private fun getAuthentication(authorizationHeader: String): UsernamePasswordAuthenticationToken {
    val token = authorizationHeader.substring(7)
    if (tokenService.isValid(token)) {
      val accountId = tokenService.getAccountId(token)
      val user = SecurityAccount(accountId)
      return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    throw UsernameNotFoundException("Unauthorized")
  }
}
