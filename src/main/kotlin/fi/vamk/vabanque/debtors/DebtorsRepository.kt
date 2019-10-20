package fi.vamk.vabanque.debtors

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface DebtorsRepository : Repository<Debtor, Long> {
  fun findById(id: Long): Debtor?
  fun findByCreditorAccountId(creditorAccountId: Long, page: Pageable): List<Debtor>
  fun findByDebtorAccountId(debtorAccountId: Long, page: Pageable): List<Debtor>
  fun save(debtor: Debtor)
  fun deleteById(id: Long)
}
