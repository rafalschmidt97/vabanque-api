package fi.vamk.vabanque.core.socket

import fi.vamk.vabanque.core.socket.domain.SocketSession

object SessionState {
  val sessions = HashMap<String, SocketSession>()
}
