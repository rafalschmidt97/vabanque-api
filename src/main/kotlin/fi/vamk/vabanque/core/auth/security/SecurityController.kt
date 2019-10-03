package fi.vamk.vabanque.core.auth.security

import org.springframework.security.core.context.SecurityContextHolder

abstract class SecurityController {
  protected fun accountId(): Long {
    val account = SecurityContextHolder.getContext().authentication.principal as SecurityAccount
    return account.id
  }
}
