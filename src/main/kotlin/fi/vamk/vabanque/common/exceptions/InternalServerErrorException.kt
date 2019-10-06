package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class InternalServerErrorException(message: String) : CustomException(message, HttpStatus.INTERNAL_SERVER_ERROR) {
  constructor() : this(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
}
