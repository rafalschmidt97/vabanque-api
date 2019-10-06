package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class NotAcceptableException(message: String) : CustomException(message, HttpStatus.NOT_ACCEPTABLE)
