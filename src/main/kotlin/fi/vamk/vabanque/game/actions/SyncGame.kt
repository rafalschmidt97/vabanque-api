package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.Game
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.SyncGameRequest
import fi.vamk.vabanque.game.findGameById
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

fun syncGame(session: WebSocketSession, request: SyncGameRequest) {
  val game = findGameById(request.gameId) ?: throw NotFoundException(Game::class, request.gameId)
  session.publish(SocketMessage(GameResponseAction.SYNC.type, game.toResponse()))
}
