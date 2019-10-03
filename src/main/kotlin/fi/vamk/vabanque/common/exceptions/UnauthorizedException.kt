package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException(message: String) : CustomException(message, HttpStatus.UNAUTHORIZED.value()) {
  constructor() : this(HttpStatus.UNAUTHORIZED.reasonPhrase)
}
