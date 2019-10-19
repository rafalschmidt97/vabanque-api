package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.game.GameMessagePayload
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.publishGame
import java.util.Date
import org.springframework.web.socket.WebSocketSession

data class ResumeGameRequest(
  override val gameId: String
) : GameMessagePayload

data class ResumedGameResponse(
  override val gameId: String,
  val startedAt: Date
) : GameMessagePayload

fun resumeGame(session: WebSocketSession, request: ResumeGameRequest) {
  val (game) = gameAdminAction(session, request)
  game.resume()

  publishGame(
    SocketMessage(
      GameResponseAction.RESUMED.type,
      ResumedGameResponse(game.id, game.currentTime.startedAt)
    ),
    game
  )
}
