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

data class StartGameRequest(
  override val gameId: String
) : GameMessagePayload

data class StartedGameResponse(
  override val gameId: String,
  val startedAt: Date
) : GameMessagePayload

fun startGame(session: WebSocketSession, request: StartGameRequest) {
  try {
    val (game) = gameAdminAction(session, request)
    game.start()

    publishGame(
      SocketMessage(
        GameResponseAction.STARTED.type,
        StartedGameResponse(game.id, game.currentTime.startedAt)
      ),
      game
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_START_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
