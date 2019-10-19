package fi.vamk.vabanque.game

import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.SessionState
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
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

fun findGameByCodeOrThrow(code: String): Game {
  return findGameByCode(code) ?: throw NotFoundException(Game::class, code)
}

fun findGameById(id: String): Game? {
  return GameState.games.filterValues { it.id == id }.values.firstOrNull()
}

fun findGameByIdOrThrow(gameId: String): Game {
  return findGameById(gameId) ?: throw NotFoundException(Game::class, gameId)
}

fun findPlayerInGame(game: Game, accountId: Long): Player? {
  return game.players.find { it.accountId == accountId }
}

fun findPlayerInGameOrThrow(game: Game, accountId: Long): Player {
  return findPlayerInGame(game, accountId)
    ?: throw NotFoundException(Player::class, accountId)
}

fun findPlayerNonAdminNonSelf(game: Game, accountId: Long): Player? {
  return game.players.find { it.accountId != accountId && !it.isAdmin }
}

fun findPlayerInGameBySession(session: WebSocketSession, game: Game): Pair<Player?, Long> {
  val accountId = session.getAccountId()
  return Pair(findPlayerInGame(game, accountId), accountId)
}

fun findPlayerInGameBySessionOrThrow(session: WebSocketSession, game: Game): Player {
  val accountId = session.getAccountId()
  return findPlayerInGameOrThrow(game, accountId)
}
