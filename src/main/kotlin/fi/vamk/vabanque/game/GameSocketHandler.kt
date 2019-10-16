package fi.vamk.vabanque.game

import fi.vamk.vabanque.core.socket.SocketHandler
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.toResponse
import java.util.UUID
import org.springframework.web.socket.WebSocketSession

class GameSocketHandler : SocketHandler() {
  override fun afterConnectionEstablished(session: WebSocketSession) {
    super.afterConnectionEstablished(session)

    publish(
      session,
      SocketMessage(
        GameSocketAction.ACCOUNTS.type,
        sessions.filterNot { it.key == session.id }.values.map { it.toResponse() })
    )
  }

  override fun handleMessage(session: WebSocketSession, message: SocketMessage) {
    when (message.type) {
      GameSocketAction.MESSAGE.type -> {
        publishAll(
          SocketMessage(
            GameSocketAction.MESSAGE.type,
            MessageResponse(UUID.randomUUID().toString(), message.payload.toString(), getAccountId(session))
          )
        )
      }
    }
  }
}
