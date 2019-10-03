package fi.vamk.vabanque.core.auth.refresh

import org.springframework.data.repository.Repository
import javax.transaction.Transactional

interface RefreshTokenRepository : Repository<RefreshToken, Long> {
  fun findByToken(token: String): RefreshToken?
  fun save(account: RefreshToken)

  @Transactional
  fun deleteByToken(token: String)

  @Transactional
  fun deleteAllByAccountId(accountId: Long)
}
