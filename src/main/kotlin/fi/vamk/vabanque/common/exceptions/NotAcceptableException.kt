package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class NotAcceptableException(type: String, message: String) : CustomException(type, message, HttpStatus.NOT_ACCEPTABLE)
