package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class RequestTimeoutException(type: String, message: String) : CustomException(type, message, HttpStatus.REQUEST_TIMEOUT)
