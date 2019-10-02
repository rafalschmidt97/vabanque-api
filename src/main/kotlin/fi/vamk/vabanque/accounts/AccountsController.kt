package fi.vamk.vabanque.accounts

import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("accounts")
class AccountsController(
  private val accountsService: AccountsService) {

  @GetMapping("{accountId}")
  fun findById(@PathVariable accountId: Long): AccountResponse {
    return accountsService.findById(accountId)
  }

  @GetMapping("self")
  fun findSelf(): SelfAccountResponse {
    val accountId = 1L // TODO: get id from context
    return accountsService.findSelfById(accountId)
  }

  @GetMapping("search")
  fun findPageByNickname(@RequestParam nickname: String, @RequestParam page: Int): List<AccountResponse> {
    return accountsService.findPageByNickname(nickname, page)
  }

  @PutMapping("self")
  fun updateSelf(@Valid @RequestBody request: AccountRequest) {
    val accountId = 1L // TODO: get id from context
    request.id = accountId
    return accountsService.update(request)
  }
}
