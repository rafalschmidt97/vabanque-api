package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameProgression
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.toResponse
import java.time.LocalTime
import org.springframework.web.socket.WebSocketSession

data class CreateGameRequest(
  val duration: LocalTime,
  val entry: String,
  val progression: List<GameProgression>
)

fun createGame(session: WebSocketSession, data: CreateGameRequest) {
  val game = Game(data.duration, data.entry, data.progression.toMutableList(), session.getAccountId())
  GameState.games[game.id] = game

  session.publish(SocketMessage(GameResponseAction.CREATED_CONFIRM.type, game.toResponse()))
}
