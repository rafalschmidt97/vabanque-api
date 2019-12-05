package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class GatewayTimeoutException(type: String, message: String) : CustomException(type, message, HttpStatus.GATEWAY_TIMEOUT)
