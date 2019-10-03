package fi.vamk.vabanque.common.exceptions

import org.springframework.http.HttpStatus

class UnprocessableEntityException(message: String) : CustomException(message, HttpStatus.UNPROCESSABLE_ENTITY.value())
