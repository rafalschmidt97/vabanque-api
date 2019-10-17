package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ForbiddenException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.Player
import fi.vamk.vabanque.game.RemoveGameConfirmResponse
import fi.vamk.vabanque.game.RemoveGameRequest
import fi.vamk.vabanque.game.findGameById
import fi.vamk.vabanque.game.publishGameExcludeSelf
import org.springframework.web.socket.WebSocketSession

fun removeGame(session: WebSocketSession, request: RemoveGameRequest) {
  val removingGame = findGameById(request.gameId) ?: throw NotFoundException(Game::class, request.gameId)
  val accountId = session.getAccountId()
  val removingPlayer = removingGame.players.find { it.accountId == accountId }
    ?: throw NotFoundException(Player::class, accountId)

  if (!removingPlayer.admin) {
    throw ForbiddenException("${Player::class.simpleName!!}(${removingPlayer.accountId}) is not an admin.")
  }

  GameState.games.remove(removingGame.id)

  publishGameExcludeSelf(
    SocketMessage(GameResponseAction.REMOVED.type, RemoveGameConfirmResponse(removingGame.id)),
    removingGame,
    session
  )
  session.publish(SocketMessage(GameResponseAction.REMOVED_CONFIRM.type, RemoveGameConfirmResponse(removingGame.id)))
}
