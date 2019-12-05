package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class InternalServerErrorException : CustomException {
  constructor(type: String, message: String) : super(type, message, HttpStatus.INTERNAL_SERVER_ERROR)
  constructor(type: String, message: String, cause: Throwable) : super(type, message, HttpStatus.INTERNAL_SERVER_ERROR, cause)
  constructor(message: String, cause: Throwable) : super(
    HttpStatus.INTERNAL_SERVER_ERROR.name,
    message,
    HttpStatus.INTERNAL_SERVER_ERROR,
    cause
  )
  constructor(message: String) : super(
    HttpStatus.INTERNAL_SERVER_ERROR.name,
    message,
    HttpStatus.INTERNAL_SERVER_ERROR
  )
  constructor(cause: Throwable) : super(
    HttpStatus.INTERNAL_SERVER_ERROR.name,
    HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
    HttpStatus.INTERNAL_SERVER_ERROR,
    cause
  )
  constructor() : this(HttpStatus.INTERNAL_SERVER_ERROR.name, HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
}
