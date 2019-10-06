package fi.vamk.vabanque.core.auth.refresh

import javax.transaction.Transactional
import org.springframework.data.repository.Repository

interface RefreshTokenRepository : Repository<RefreshToken, Long> {
  fun findByToken(token: String): RefreshToken?
  fun save(account: RefreshToken)

  @Transactional
  fun deleteByToken(token: String)

  @Transactional
  fun deleteAllByAccountId(accountId: Long)
}
