package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class BadRequestException(type: String, message: String) : CustomException(type, message, HttpStatus.BAD_REQUEST) {
  constructor(message: String) : this(HttpStatus.BAD_REQUEST.name, message)
}
