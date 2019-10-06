package fi.vamk.vabanque.accounts

import fi.vamk.vabanque.core.auth.security.SecurityController
import io.swagger.annotations.Api
import javax.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("accounts")
@Api(tags = ["Accounts"])
class AccountsController(
  private val accountsService: AccountsService
) : SecurityController() {

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
