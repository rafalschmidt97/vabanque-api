package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnprocessableEntityException(type: String, message: String) : CustomException(type, message, HttpStatus.UNPROCESSABLE_ENTITY)
