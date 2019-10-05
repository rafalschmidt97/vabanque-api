package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class ServiceUnavailableException(message: String) : CustomException(message, HttpStatus.SERVICE_UNAVAILABLE) {
  constructor() : this(HttpStatus.SERVICE_UNAVAILABLE.reasonPhrase)
}
