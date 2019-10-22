package fi.vamk.vabanque.core.socket.domain

import org.springframework.web.socket.WebSocketSession

data class SocketSession(
  val accountId: Long,
  val session: WebSocketSession
)
