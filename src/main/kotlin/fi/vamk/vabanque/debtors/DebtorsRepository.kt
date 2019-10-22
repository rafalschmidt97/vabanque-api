package fi.vamk.vabanque.debtors

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.Repository

interface DebtorsRepository : Repository<Debtor, Long> {
  fun findByIdAndIsRemovedFalse(id: Long): Debtor?
  fun findByCreditorAccountIdAndIsRemovedFalse(creditorAccountId: Long, page: Pageable): List<Debtor>
  fun findByDebtorAccountIdAndIsRemovedFalse(debtorAccountId: Long, page: Pageable): List<Debtor>
  fun save(debtor: Debtor)
  fun saveAll(debtors: Iterable<Debtor>)
}
