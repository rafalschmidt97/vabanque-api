package fi.vamk.vabanque.core.socket.domain

data class SocketMessage(
  val type: String,
  val payload: Any
)
