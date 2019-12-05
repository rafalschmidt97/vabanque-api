package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class ServiceUnavailableException(type: String, message: String) : CustomException(type, message, HttpStatus.SERVICE_UNAVAILABLE) {
  constructor() : this(HttpStatus.SERVICE_UNAVAILABLE.name, HttpStatus.SERVICE_UNAVAILABLE.reasonPhrase)
}
