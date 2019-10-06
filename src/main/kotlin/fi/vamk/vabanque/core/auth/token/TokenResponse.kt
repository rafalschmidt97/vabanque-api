package fi.vamk.vabanque.core.auth.token

data class TokenResponse(
  val accessToken: String,
  val refreshToken: String
)
