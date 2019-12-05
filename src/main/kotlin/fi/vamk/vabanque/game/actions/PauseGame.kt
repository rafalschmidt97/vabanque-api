package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.publishGame
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class PauseGameRequest(
  override val gameId: String
) : GameMessagePayload

data class PausedGameResponse(
  override val gameId: String,
  val pausedAt: Date
) : GameMessagePayload

fun pauseGame(session: WebSocketSession, request: PauseGameRequest) {
  try {
    val (game) = gameAction(session, request)
    game.pause()

    publishGame(
      SocketMessage(
        GameResponseAction.PAUSED.type,
        PausedGameResponse(game.id, game.currentTime.pausedAt!!)
      ),
      game
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_PAUSE_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
