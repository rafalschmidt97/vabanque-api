package fi.vamk.vabanque.core.auth.refresh

import fi.vamk.vabanque.accounts.Account
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
data class RefreshToken(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long,

  @Column(nullable = false)
  var issuedAt: Date,

  @Column(nullable = false)
  var expiredAt: Date,

  @Column(nullable = false, length = 50)
  var token: String,

  @Column(name = "account_id", nullable = false)
  var accountId: Long,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", insertable = false, updatable = false)
  private var account: Account? = null
) {
  constructor(expiredAt: Date, token: String, accountId: Long) :
    this(id = 0, issuedAt = Date(), expiredAt = expiredAt, token = token, accountId = accountId)

  fun isExpired(): Boolean {
    return expiredAt.before(Date())
  }
}
