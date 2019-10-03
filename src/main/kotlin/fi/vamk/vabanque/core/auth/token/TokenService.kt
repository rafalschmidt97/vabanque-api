package fi.vamk.vabanque.core.auth.token

import fi.vamk.vabanque.common.utilities.getRandomBase64
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

  @Value("\${jwt.secret}")
  private lateinit var secret: String

  @Value("\${jwt.expiration}")
  private lateinit var expiration: String

  fun generateAccess(accountId: Long): String {
    return Jwts.builder()
      .setSubject(accountId.toString())
      .setIssuedAt(Date())
      .setExpiration(Date(System.currentTimeMillis() + expiration.toInt()))
      .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
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
      Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
    } catch (e: Exception) {
      null
    }
  }
}
