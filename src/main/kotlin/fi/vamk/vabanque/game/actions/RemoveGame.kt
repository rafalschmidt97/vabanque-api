package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameMessagePayload
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.publishGameExcludeSelf
import org.springframework.web.socket.WebSocketSession

data class RemoveGameRequest(
  override val gameId: String
) : GameMessagePayload

data class RemovedGameResponse(
  override val gameId: String
) : GameMessagePayload

data class RemovedGameConfirmResponse(
  override val gameId: String
) : GameMessagePayload

fun removeGame(session: WebSocketSession, request: RemoveGameRequest) {
  val (game) = gameAdminAction(session, request)
  GameState.games.remove(game.id)

  publishGameExcludeSelf(
    SocketMessage(GameResponseAction.REMOVED.type, RemovedGameResponse(game.id)),
    game,
    session
  )
  session.publish(SocketMessage(GameResponseAction.REMOVED_CONFIRM.type, RemovedGameConfirmResponse(game.id)))
}
