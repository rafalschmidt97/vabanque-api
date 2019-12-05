package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.domain.GameProgression
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.publishGame
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class RaiseGameRequest(
  override val gameId: String
) : GameMessagePayload

data class RaisedGameResponse(
  override val gameId: String,
  val progression: GameProgression,
  val pausedAt: Date,
  val startedAt: Date
) : GameMessagePayload

fun raiseGame(session: WebSocketSession, request: RaiseGameRequest) {
  try {
    val (game) = gameAdminAction(session, request)
    game.raise()

    publishGame(
      SocketMessage(
        GameResponseAction.RAISED.type,
        RaisedGameResponse(game.id, game.currentProgression, game.previousTime!!.pausedAt!!, game.currentTime.startedAt)
      ),
      game
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_RAISE_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
