package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class BadGatewayException(message: String) : CustomException(message, HttpStatus.BAD_GATEWAY)
