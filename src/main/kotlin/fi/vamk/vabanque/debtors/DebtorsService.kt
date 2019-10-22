package fi.vamk.vabanque.debtors

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.common.data.showPage
import fi.vamk.vabanque.common.exceptions.ForbiddenException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class DebtorsService(
  private val debtorsRepository: DebtorsRepository
) {

  fun findDebtorPage(creditorAccountId: Long, page: Int): List<DebtorResponse> {
    return debtorsRepository.findByCreditorAccountIdAndIsRemovedFalse(creditorAccountId, showPage(page))
      .map { it.toDebtorResponse() }
  }

  fun findCreditorPage(debtorAccountId: Long, page: Int): List<DebtorResponse> {
    return debtorsRepository.findByDebtorAccountIdAndIsRemovedFalse(debtorAccountId, showPage(page))
      .map { it.toCreditorResponse() }
  }

  fun remove(id: Long, creditorAccountId: Long) {
    val debtor = debtorsRepository.findByIdAndIsRemovedFalse(id)
      ?: throw NotFoundException(Debtor::class, id)

    if (debtor.creditorAccountId != creditorAccountId) {
      throw ForbiddenException(
        "${Account::class.simpleName!!}($creditorAccountId) is not a creditor for ${Debtor::class.simpleName!!}($id)."
      )
    }

    debtor.remove()
    debtorsRepository.save(debtor)
  }

  fun addRange(debtors: List<Debtor>) {
    debtorsRepository.saveAll(debtors)
  }
}
