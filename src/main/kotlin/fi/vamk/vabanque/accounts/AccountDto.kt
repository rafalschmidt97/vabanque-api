package fi.vamk.vabanque.accounts

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class AccountResponse(
  val id: Long,
  val nickname: String?,
  val avatar: String?
)

fun Account.toResponse() = AccountResponse(id, nickname, avatar)

data class SelfAccountResponse(
  val id: Long,
  val email: String,
  val nickname: String?,
  val phoneNumber: String?,
  val avatar: String?
)

fun Account.toSelfResponse() = SelfAccountResponse(id, email, nickname, phoneNumber, avatar)

data class AccountRequest(
  var id: Long,

  @NotBlank
  @Size(min = 3, max = 30)
  val nickname: String,

  @NotBlank
  @Size(min = 9, max = 30)
  val phoneNumber: String,

  @NotBlank
  @Size(min = 10, max = 2048)
  val avatar: String
)
