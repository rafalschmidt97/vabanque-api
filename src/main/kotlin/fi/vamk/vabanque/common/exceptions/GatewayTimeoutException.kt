package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class GatewayTimeoutException(message: String) : CustomException(message, HttpStatus.GATEWAY_TIMEOUT.value())
