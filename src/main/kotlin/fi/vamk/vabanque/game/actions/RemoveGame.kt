package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.domain.Game
import fi.vamk.vabanque.game.domain.GameStatus
import fi.vamk.vabanque.game.dto.GameMessagePayload
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

  if (game.status == GameStatus.FINISHED) {
    throw ConflictException("${Game::class.simpleName!!}(${game.id}) is finished. Send the ranking instead.")
  }

  GameState.games.remove(game.id)

  publishGameExcludeSelf(
    SocketMessage(GameResponseAction.REMOVED.type, RemovedGameResponse(game.id)),
    game,
    session
  )
  session.publish(
    SocketMessage(
      GameResponseAction.REMOVED_CONFIRM.type,
      RemovedGameConfirmResponse(game.id)
    )
  )
}
