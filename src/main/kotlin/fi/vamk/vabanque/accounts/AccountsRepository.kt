package fi.vamk.vabanque.accounts

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface AccountsRepository : Repository<Account, Long> {
  fun findById(id: Long): Account?
  fun findByEmail(email: String): Account?
  fun findByNicknameContainingIgnoreCase(nickname: String, page: Pageable): List<Account>
  fun existsByNickname(nickname: String): Boolean
  fun existsByEmail(email: String): Boolean
  fun existsById(id: Long): Boolean
  fun save(account: Account)
}
