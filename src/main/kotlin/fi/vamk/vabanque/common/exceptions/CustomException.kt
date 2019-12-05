package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

abstract class CustomException : RuntimeException {
  var type: String = HttpStatus.INTERNAL_SERVER_ERROR.name
  var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

  constructor(type: String, message: String, status: HttpStatus) : super(message) {
    this.type = type
    this.status = status
  }

  constructor(type: String, message: String, status: HttpStatus, cause: Throwable) : super(message, cause) {
    this.type = type
    this.status = status
  }
}
