package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.LeaveGameConfirmResponse
import fi.vamk.vabanque.game.LeaveGameRequest
import fi.vamk.vabanque.game.Player
import fi.vamk.vabanque.game.findGameById
import fi.vamk.vabanque.game.publishGame
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

fun leaveGame(session: WebSocketSession, request: LeaveGameRequest) {
  val leavingGame = findGameById(request.gameId) ?: throw NotFoundException(Game::class, request.gameId)
  val accountId = session.getAccountId()
  val leavingPlayer = leavingGame.players.find { it.accountId == accountId }
    ?: throw NotFoundException(Player::class, accountId)

  if (leavingGame.players.size > 1) {
    if (leavingPlayer.admin) {
      val nonAdminOtherPlayer = leavingGame.players.find { it.accountId != accountId && !it.admin }!!
      nonAdminOtherPlayer.makeAdmin()
      leavingGame.players.remove(leavingPlayer)
      publishGame(SocketMessage(GameResponseAction.SYNC.type, leavingGame.toResponse()), leavingGame)
    } else {
      leavingGame.players.remove(leavingPlayer)
      publishGame(SocketMessage(GameResponseAction.LEFT.type, leavingPlayer.toResponse()), leavingGame)
    }
  } else {
    GameState.games.remove(leavingGame.id)
  }

  session.publish(SocketMessage(GameResponseAction.LEFT_CONFIRM.type, LeaveGameConfirmResponse(leavingGame.id)))
}
