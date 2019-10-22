package fi.vamk.vabanque.debtors

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.common.exceptions.InternalServerErrorException
import java.util.Date

data class DebtorResponse(
  val id: Long,
  val accountId: Long,
  val nickname: String?,
  val phoneNumber: String?,
  val avatar: String?,
  val amount: String,
  val createdAt: Date
)

fun Debtor.toDebtorResponse(): DebtorResponse {
  val debtor = debtorAccount ?: throw InternalServerErrorException(
    "${Account::class.simpleName!!} in ${Debtor::class.simpleName!!} cannot be empty to create a response."
  )

  return DebtorResponse(
    id,
    debtor.id,
    debtor.nickname,
    debtor.phoneNumber,
    debtor.avatar,
    amount,
    createdAt
  )
}

fun Debtor.toCreditorResponse(): DebtorResponse {
  val creditor = creditorAccount ?: throw InternalServerErrorException(
    "${Account::class.simpleName!!} in ${Debtor::class.simpleName!!} cannot be empty to create a response."
  )

  return DebtorResponse(
    id,
    creditor.id,
    creditor.nickname,
    creditor.phoneNumber,
    creditor.avatar,
    amount,
    createdAt
  )
}

data class RankedDebtorResponse(
  val id: Long,
  var creditorAccountId: Long,
  var debtorAccountId: Long,
  val createdAt: Date
)

fun Debtor.toRankedResponse() = RankedDebtorResponse(id, creditorAccountId, debtorAccountId, createdAt)
