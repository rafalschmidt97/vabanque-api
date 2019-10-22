package fi.vamk.vabanque.core.socket

import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.common.utilities.logger
import fi.vamk.vabanque.core.mapper.Mapper
import fi.vamk.vabanque.core.socket.SessionState.sessions
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.domain.SocketSession
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

abstract class SocketHandler : TextWebSocketHandler() {
  private val logger by logger()

  override fun afterConnectionEstablished(session: WebSocketSession) {
    sessions[session.id] = SocketSession(session.getAccountId(), session)
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    if (sessions.containsKey(session.id)) {
      sessions.remove(session.id)
    }
  }

  override fun handleTextMessage(session: WebSocketSession, textMessage: TextMessage) {
    val message = textMessageToObject<SocketMessage>(textMessage)

    try {
      handleMessage(session, message)
    } catch (e: CustomException) {
      session.publishCustomError(e)
    } catch (e: Exception) {
      session.publishError(e, logger)
    }
  }

  protected abstract fun handleMessage(session: WebSocketSession, message: SocketMessage)

  protected inline fun <reified T> payloadToObject(payload: Any): T {
    return Mapper.jackson.convertValue(payload, T::class.java)
  }

  private inline fun <reified T> textMessageToObject(textMessage: TextMessage): T {
    val json = Mapper.jackson.readTree(textMessage.payload)
    return Mapper.jackson.treeToValue(json, T::class.java)
  }
}
