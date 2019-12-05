package fi.vamk.vabanque.debtors

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.common.exceptions.ConflictException
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Debtor(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long,

  @Column(name = "creditor_account_id", nullable = false)
  var creditorAccountId: Long,

  @Column(name = "debtor_account_id", nullable = false)
  var debtorAccountId: Long,

  @Column(nullable = false, length = 30)
  var amount: String,

  @Column(nullable = false)
  var createdAt: Date = Date(),

  @Column
  var isRemoved: Boolean = false,

  @Column
  var removedAt: Date? = null,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creditor_account_id", insertable = false, updatable = false)
  var creditorAccount: Account? = null,

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "debtor_account_id", insertable = false, updatable = false)
  var debtorAccount: Account? = null
) {
  constructor(creditorAccountId: Long, debtorAccountId: Long, amount: String) :
    this(
      id = 0,
      creditorAccountId = creditorAccountId,
      debtorAccountId = debtorAccountId,
      amount = amount
    )

  fun remove() {
    if (isRemoved) {
      throw ConflictException("DEBTOR_ALREADY_REMOVED", "${Debtor::class.simpleName!!}($id) is already removed.")
    }

    isRemoved = true
    removedAt = Date()
  }
}
