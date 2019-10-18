package fi.vamk.vabanque.core.socket

import org.springframework.web.socket.WebSocketSession

object SessionState {
  val sessions = HashMap<String, SocketSession>()
}

data class SocketSession(
  val accountId: Long,
  val session: WebSocketSession
)

data class SocketMessage(
  val type: String,
  val payload: Any
)
