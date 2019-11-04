package fi.vamk.vabanque.accounts

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface AccountsRepository : Repository<Account, Long> {
  fun findById(id: Long): Account?
  fun findByEmail(email: String): Account?
  fun findByNicknameContainingIgnoreCase(nickname: String, page: Pageable): List<Account>
  fun existsByNicknameAndIdNot(nickname: String, id: Long): Boolean
  fun existsByEmail(email: String): Boolean
  fun existsByIdIn(ids: List<Long>): Boolean
  fun save(account: Account)
}
