package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.JoinGameRequest
import fi.vamk.vabanque.game.Player
import fi.vamk.vabanque.game.findGameByCode
import fi.vamk.vabanque.game.publishGameExcludeSelf
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

fun joinGame(session: WebSocketSession, request: JoinGameRequest) {
  val joiningGame = findGameByCode(request.code) ?: throw NotFoundException(Game::class, request.code)
  val accountId = session.getAccountId()
  var player = joiningGame.players.find { it.accountId == accountId }

  if (player == null) {
    player = Player(accountId)
    joiningGame.players.add(player)

    publishGameExcludeSelf(
      SocketMessage(GameResponseAction.JOINED.type, player.toResponse()),
      joiningGame,
      session
    )
  } else {
    player.reconnect()

    publishGameExcludeSelf(
      SocketMessage(GameResponseAction.RECONNECTED.type, player.toResponse()),
      joiningGame,
      session
    )
  }

  session.publish(SocketMessage(GameResponseAction.JOINED_CONFIRM.type, joiningGame.toResponse()))
}
