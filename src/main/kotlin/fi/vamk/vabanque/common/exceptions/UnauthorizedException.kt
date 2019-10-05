package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException(message: String) : CustomException(message, HttpStatus.UNAUTHORIZED) {
  constructor() : this(HttpStatus.UNAUTHORIZED.reasonPhrase)
}
