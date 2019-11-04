package fi.vamk.vabanque.accounts

import fi.vamk.vabanque.common.data.showPage
import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import org.springframework.stereotype.Service

@Service
class AccountsService(
  private val accountsRepository: AccountsRepository
) {

  fun findById(id: Long): AccountResponse {
    return accountsRepository.findById(id)?.toResponse()
      ?: throw NotFoundException(Account::class, id)
  }

  fun findSelfById(id: Long): SelfAccountResponse {
    return accountsRepository.findById(id)?.toSelfResponse()
      ?: throw NotFoundException(Account::class, id)
  }

  fun findPageByNickname(nickname: String, page: Int): List<AccountResponse> {
    return accountsRepository.findByNicknameContainingIgnoreCase(nickname, showPage(page))
      .map { it.toResponse() }
  }

  fun update(request: AccountRequest) {
    val account = accountsRepository.findById(request.id)
      ?: throw NotFoundException(Account::class, request.id)

    if (accountsRepository.existsByNicknameAndIdNot(request.nickname, request.id)) {
      throw ConflictException.alreadyExists(Account::class, "nickname=${request.nickname}")
    }

    account.update(request.nickname, request.phoneNumber, request.avatar)
    accountsRepository.save(account)
  }

  fun existsRange(ids: List<Long>): Boolean {
    return accountsRepository.existsByIdIn(ids)
  }
}
