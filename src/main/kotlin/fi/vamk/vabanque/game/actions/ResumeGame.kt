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

data class ResumeGameRequest(
  override val gameId: String
) : GameMessagePayload

data class ResumedGameResponse(
  override val gameId: String,
  val startedAt: Date
) : GameMessagePayload

fun resumeGame(session: WebSocketSession, request: ResumeGameRequest) {
  try {
    val (game) = gameAdminAction(session, request)
    game.resume()

    publishGame(
      SocketMessage(
        GameResponseAction.RESUMED.type,
        ResumedGameResponse(game.id, game.currentTime.startedAt)
      ),
      game
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_RESUME_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
