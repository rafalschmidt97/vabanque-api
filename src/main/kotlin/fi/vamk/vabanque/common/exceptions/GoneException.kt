package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class GoneException(message: String) : CustomException(message, HttpStatus.GONE.value())
