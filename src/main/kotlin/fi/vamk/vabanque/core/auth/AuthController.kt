package fi.vamk.vabanque.core.auth

import fi.vamk.vabanque.core.auth.security.SecurityController
import fi.vamk.vabanque.core.auth.token.TokenResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("auth")
class AuthController(
  private val authService: AuthService) : SecurityController() {

  @PostMapping("sign-in")
  fun signIn(@Valid @RequestBody request: SignInRequest): TokenResponse {
    return authService.signIn(request)
  }

  @PostMapping("sign-up")
  fun signUp(@Valid @RequestBody request: SignUpRequest): TokenResponse {
    return authService.signUp(request)
  }

  @PostMapping("refresh")
  fun refresh(@Valid @RequestBody request: RefreshRequest): TokenResponse {
    return authService.refresh(request)
  }

  @PostMapping("logout")
  fun logout(@Valid @RequestBody request: LogoutRequest) {
    return authService.logout(request)
  }

  @PostMapping("logout/all")
  fun logoutAll() {
    return authService.logoutAllDevices(accountId())
  }
}
