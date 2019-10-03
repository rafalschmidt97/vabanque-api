package fi.vamk.vabanque.accounts

import fi.vamk.vabanque.core.auth.security.SecurityController
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("accounts")
class AccountsController(
  private val accountsService: AccountsService) : SecurityController() {

  @GetMapping("{accountId}")
  fun findById(@PathVariable accountId: Long): AccountResponse {
    return accountsService.findById(accountId)
  }

  @GetMapping("self")
  fun findSelf(): SelfAccountResponse {
    return accountsService.findSelfById(accountId())
  }

  @GetMapping("search")
  fun findPageByNickname(@RequestParam nickname: String, @RequestParam page: Int): List<AccountResponse> {
    return accountsService.findPageByNickname(nickname, page)
  }

  @PutMapping("self")
  fun updateSelf(@Valid @RequestBody request: AccountRequest) {
    request.id = accountId()
    return accountsService.update(request)
  }
}
