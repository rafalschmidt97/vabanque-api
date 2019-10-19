package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.game.GameMessagePayload
import fi.vamk.vabanque.game.GameProgression
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.publishGame
import java.util.Date
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
  val (game) = gameAdminAction(session, request)
  game.raise()

  publishGame(
    SocketMessage(
      GameResponseAction.RAISED.type,
      RaisedGameResponse(game.id, game.currentProgression, game.previousTime!!.pausedAt!!, game.currentTime.startedAt)
    ),
    game
  )
}
