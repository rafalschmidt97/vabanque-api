package fi.vamk.vabanque.core.socket

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import fi.vamk.vabanque.core.auth.security.SecurityAccount
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

class SocketAccount(val id: Long, val session: WebSocketSession)
data class SocketAccountResponse(val id: Long)
fun SocketAccount.toResponse() = SocketAccountResponse(id)
class SocketMessage(val type: String, val payload: Any)

abstract class SocketHandler : TextWebSocketHandler() {
  protected val sessions = HashMap<String, SocketAccount>()

  override fun afterConnectionEstablished(session: WebSocketSession) {
    val account = SocketAccount(getAccountId(session), session)
    sessions[session.id] = account
    publishExcludeSelf(session, SocketMessage(SocketAction.JOINED.type, account.toResponse()))
  }

  override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
    if (sessions.containsKey(session.id)) {
      val account = sessions[session.id]!!
      sessions.remove(session.id)
      publishAll(SocketMessage(SocketAction.LEFT.type, account.toResponse()))
    }
  }

  override fun handleTextMessage(session: WebSocketSession, textMessage: TextMessage) {
    val message = textMessageToObject<SocketMessage>(textMessage)
    handleMessage(session, message)
  }

  protected abstract fun handleMessage(session: WebSocketSession, message: SocketMessage)

  protected fun publish(session: WebSocketSession, message: SocketMessage) {
    session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(message)))
  }

  protected fun publishAll(message: SocketMessage) {
    sessions.forEach { publish(it.value.session, message) }
  }

  protected fun publishExcludeSelf(session: WebSocketSession, message: SocketMessage) {
    sessions.filterNot { it.key == session.id }.forEach { publish(it.value.session, message) }
  }

  protected fun getAccountId(session: WebSocketSession): Long {
    val authenticationToken = session.principal as UsernamePasswordAuthenticationToken
    val account = authenticationToken.principal as SecurityAccount
    return account.id
  }

  private inline fun <reified T> textMessageToObject(textMessage: TextMessage): T {
    val mapper = jacksonObjectMapper()
    val json = mapper.readTree(textMessage.payload)
    return mapper.treeToValue(json, T::class.java)
  }
}
