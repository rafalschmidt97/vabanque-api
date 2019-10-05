package fi.vamk.vabanque.core.exceptions

import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.common.utilities.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class CustomExceptionHandler {
  val logger by logger()

  @ExceptionHandler(value = [(CustomException::class)])
  fun handleCustomException(exception: CustomException, request: WebRequest): ResponseEntity<ExceptionResponse> {
    return ResponseEntity(
      ExceptionResponse(
        exception.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
      ),
      exception.status)
  }

  @ExceptionHandler(value = [(Exception::class)])
  fun handleUnknownException(exception: Exception, request: WebRequest): ResponseEntity<ExceptionResponse> {
    logger.warning(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    exception.printStackTrace()

    return ResponseEntity(
      ExceptionResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase
      ),
      HttpStatus.INTERNAL_SERVER_ERROR)
  }
}
