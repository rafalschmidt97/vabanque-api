package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class GoneException(type: String, message: String) : CustomException(type, message, HttpStatus.GONE)
