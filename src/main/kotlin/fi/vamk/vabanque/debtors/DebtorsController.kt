package fi.vamk.vabanque.debtors

import fi.vamk.vabanque.core.auth.security.SecurityController
import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("debtors")
@Api(tags = ["Debtors"])
class DebtorsController(
  private val debtorsService: DebtorsService
) : SecurityController() {

  @GetMapping("self")
  fun findDebtorsPage(@RequestParam page: Int): List<DebtorResponse> {
    return debtorsService.findDebtorPage(accountId(), page)
  }

  @GetMapping("self/creditors")
  fun findCreditorPage(@RequestParam page: Int): List<DebtorResponse> {
    return debtorsService.findCreditorPage(accountId(), page)
  }

  @DeleteMapping("{debtorId}")
  fun removeById(@PathVariable debtorId: Long) {
    return debtorsService.remove(debtorId, accountId())
  }
}
