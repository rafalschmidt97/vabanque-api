package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

abstract class CustomException(message: String, val status: HttpStatus) : RuntimeException(message)
