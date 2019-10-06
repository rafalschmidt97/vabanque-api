package fi.vamk.vabanque.core.auth

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class SignInRequest(
  @NotBlank
  @Size(min = 3, max = 50)
  val email: String,

  @NotBlank
  @Size(min = 3, max = 50)
  val password: String
)

// TODO: use google/facebook for auth
data class SignUpRequest(
  @NotBlank
  @Size(min = 3, max = 50)
  val email: String,

  @NotBlank
  @Size(min = 3, max = 50)
  val password: String
)

data class RefreshRequest(
  @NotBlank
  val refreshToken: String
)

data class LogoutRequest(
  @NotBlank
  val refreshToken: String
)
