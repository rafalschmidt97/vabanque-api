package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnauthorizedException(type: String, message: String) : CustomException(type, message, HttpStatus.UNAUTHORIZED) {
  constructor(message: String) : this(HttpStatus.UNAUTHORIZED.name, message)
  constructor() : this(HttpStatus.UNAUTHORIZED.name, HttpStatus.UNAUTHORIZED.reasonPhrase)
}
