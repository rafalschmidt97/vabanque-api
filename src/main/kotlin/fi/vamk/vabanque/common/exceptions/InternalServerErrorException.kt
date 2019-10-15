package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class InternalServerErrorException : CustomException {
  constructor(message: String) : super(message, HttpStatus.INTERNAL_SERVER_ERROR)
  constructor(message: String, cause: Throwable) : super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause)
  constructor(cause: Throwable) : super(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, HttpStatus.INTERNAL_SERVER_ERROR, cause)
  constructor() : this(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
}
