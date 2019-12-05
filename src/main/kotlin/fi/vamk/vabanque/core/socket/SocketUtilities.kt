package fi.vamk.vabanque.core.socket

import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.auth.security.SecurityAccount
import fi.vamk.vabanque.core.exceptions.HttpExceptionResponse
import fi.vamk.vabanque.core.mapper.Mapper
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import java.util.logging.Logger
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

fun WebSocketSession.publish(message: SocketMessage) {
  val jsonMessage = Mapper.jackson.writeValueAsString(message)
  sendMessage(TextMessage(jsonMessage))
}

fun WebSocketSession.publishCustomError(e: CustomException) {
  publish(
    SocketMessage(
      SocketAction.ERROR.type,
      HttpExceptionResponse(
        e.type,
        e.message ?: HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
        e.status.value())
    )
  )
}

fun WebSocketSession.publishError(e: Exception, logger: Logger) {
  logger.warning(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
  e.printStackTrace()

  publish(
    SocketMessage(
      SocketAction.ERROR.type,
      HttpExceptionResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.name,
        HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
        HttpStatus.INTERNAL_SERVER_ERROR.value())
    )
  )
}

fun WebSocketSession.getAccountId(): Long {
  val authenticationToken = principal as UsernamePasswordAuthenticationToken
  val account = authenticationToken.principal as SecurityAccount
  return account.id
}
