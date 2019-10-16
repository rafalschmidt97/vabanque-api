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
      val token = header.substring(7)
      val auth = getAuthentication(token)
      SecurityContextHolder.getContext().authentication = auth
    } else if (request.parameterMap["access_token"] != null) {
      val token = request.parameterMap["access_token"]?.get(0)!!
      val auth = getAuthentication(token)
      SecurityContextHolder.getContext().authentication = auth
    }

    chain.doFilter(request, response)
  }

  private fun getAuthentication(accessToken: String): UsernamePasswordAuthenticationToken {
    if (tokenService.isValid(accessToken)) {
      val accountId = tokenService.getAccountId(accessToken)
      val user = SecurityAccount(accountId)
      return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

    throw UsernameNotFoundException("Unauthorized")
  }
}
