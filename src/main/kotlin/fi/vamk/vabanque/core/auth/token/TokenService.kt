package fi.vamk.vabanque.core.auth.token

import fi.vamk.vabanque.common.utilities.getRandomBase64
import fi.vamk.vabanque.core.auth.AuthProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import org.springframework.stereotype.Service

@Service
class TokenService(private val properties: AuthProperties) {
  fun generateAccess(accountId: Long): String {
    return Jwts.builder()
      .setSubject(accountId.toString())
      .setIssuedAt(Date())
      .setExpiration(Date(System.currentTimeMillis() + properties.expiration.toInt()))
      .signWith(SignatureAlgorithm.HS512, properties.secret.toByteArray())
      .compact()
  }

  fun generateRefresh(): String {
    return getRandomBase64()
  }

  fun isValid(token: String): Boolean {
    val claims = getClaims(token)

    return claims != null &&
      claims.subject != null && claims.expiration != null &&
      Date().before(claims.expiration)
  }

  fun getAccountId(token: String): Long {
    val claims = getClaims(token)
    return claims?.subject?.toLong() ?: 0
  }

  private fun getClaims(token: String): Claims? {
    return try {
      Jwts.parser().setSigningKey(properties.secret.toByteArray()).parseClaimsJws(token).body
    } catch (e: Exception) {
      null
    }
  }
}
