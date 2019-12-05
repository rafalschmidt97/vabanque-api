package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class ForbiddenException(type: String, message: String) : CustomException(type, message, HttpStatus.FORBIDDEN) {
  constructor(message: String) : this(HttpStatus.FORBIDDEN.name, message)
}
