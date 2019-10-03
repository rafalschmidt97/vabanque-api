package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class RequestTimeoutException(message: String) : CustomException(message, HttpStatus.REQUEST_TIMEOUT.value())
