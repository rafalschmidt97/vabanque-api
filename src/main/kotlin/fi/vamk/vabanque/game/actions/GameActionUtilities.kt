package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ForbiddenException
import fi.vamk.vabanque.game.domain.Game
import fi.vamk.vabanque.game.domain.Player
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.findGameByIdOrThrow
import fi.vamk.vabanque.game.findPlayerInGameBySessionOrThrow
import org.springframework.web.socket.WebSocketSession

fun gameAction(session: WebSocketSession, request: GameMessagePayload): Pair<Game, Player> {
  val game = findGameByIdOrThrow(request.gameId)
  val player = findPlayerInGameBySessionOrThrow(session, game)

  return Pair(game, player)
}

fun gameAdminAction(session: WebSocketSession, request: GameMessagePayload): Pair<Game, Player> {
  val (game, player) = gameAction(session, request)

  if (!player.isAdmin) {
    throw ForbiddenException("${Player::class.simpleName!!}(${player.accountId}) is not an admin.")
  }

  return Pair(game, player)
}
