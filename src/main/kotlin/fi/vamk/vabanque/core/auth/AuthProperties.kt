package fi.vamk.vabanque.core.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("vabanque.jwt")
class AuthProperties {
  lateinit var secret: String
  lateinit var expiration: String
}
