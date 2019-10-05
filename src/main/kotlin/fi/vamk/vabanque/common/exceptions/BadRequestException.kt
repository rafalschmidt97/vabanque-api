package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class BadRequestException(message: String) : CustomException(message, HttpStatus.BAD_REQUEST)
