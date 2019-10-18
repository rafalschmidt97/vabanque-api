package fi.vamk.vabanque.game

import fi.vamk.vabanque.core.socket.SessionState
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import org.springframework.web.socket.WebSocketSession

fun publishGame(message: SocketMessage, game: Game) {
  SessionState.sessions.filterValues { session ->
    game.players.any { game -> game.accountId == session.accountId }
  }.forEach { it.value.session.publish(message) }
}

fun publishGameExcludeSelf(message: SocketMessage, game: Game, selfSession: WebSocketSession) {
  SessionState.sessions.filter { (id, session) ->
    game.players.any { player -> player.accountId == session.accountId && id != selfSession.id }
  }.forEach { it.value.session.publish(message) }
}

fun findGameByCode(code: String): Game? {
  return GameState.games.filterValues { it.code == code }.values.firstOrNull()
}

fun findGameById(id: String): Game? {
  return GameState.games.filterValues { it.id == id }.values.firstOrNull()
}
