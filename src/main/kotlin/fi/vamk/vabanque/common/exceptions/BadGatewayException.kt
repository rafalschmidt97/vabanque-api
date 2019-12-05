package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class BadGatewayException(type: String, message: String) : CustomException(type, message, HttpStatus.BAD_GATEWAY)
