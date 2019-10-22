package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.dto.toResponse
import org.springframework.web.socket.WebSocketSession

data class SyncGameRequest(
  override val gameId: String
) : GameMessagePayload

fun syncGame(session: WebSocketSession, request: SyncGameRequest) {
  val (game) = gameAction(session, request)
  session.publish(SocketMessage(GameResponseAction.SYNC.type, game.toResponse()))
}
