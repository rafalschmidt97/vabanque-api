package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

abstract class CustomException : RuntimeException {
  var status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

  constructor(message: String, status: HttpStatus) : super(message) {
    this.status = status
  }

  constructor(message: String, status: HttpStatus, cause: Throwable) : super(message, cause) {
    this.status = status
  }
}
