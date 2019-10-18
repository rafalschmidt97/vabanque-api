package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.CreateGameRequest
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

fun createGame(session: WebSocketSession, data: CreateGameRequest) {
  val game = Game(data.duration, data.entry, data.progression, session.getAccountId())
  GameState.games[game.id] = game

  session.publish(SocketMessage(GameResponseAction.CREATED_CONFIRM.type, game.toResponse()))
}
