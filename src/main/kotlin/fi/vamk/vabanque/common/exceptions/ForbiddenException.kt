package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class ForbiddenException(message: String) : CustomException(message, HttpStatus.FORBIDDEN)
