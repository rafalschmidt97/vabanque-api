package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameMessagePayload
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.PlayerResponse
import fi.vamk.vabanque.game.findPlayerNonAdminNonSelf
import fi.vamk.vabanque.game.publishGame
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

data class LeaveGameRequest(
  override val gameId: String
) : GameMessagePayload

data class LeftGameResponse(
  override val gameId: String,
  val player: PlayerResponse
) : GameMessagePayload

data class LeftGameConfirmResponse(
  override val gameId: String
) : GameMessagePayload

fun leaveGame(session: WebSocketSession, request: LeaveGameRequest) {
  val (game, player) = gameAction(session, request)

  if (game.players.size > 1) {
    if (player.isAdmin) {
      val nonAdminOtherPlayer = findPlayerNonAdminNonSelf(game, player.accountId)!!
      nonAdminOtherPlayer.makeAdmin()
      game.players.remove(player)
      publishGame(SocketMessage(GameResponseAction.SYNC.type, game.toResponse()), game)
    } else {
      game.players.remove(player)
      publishGame(SocketMessage(GameResponseAction.LEFT.type, LeftGameResponse(game.id, player.toResponse())), game)
    }
  } else {
    GameState.games.remove(game.id)
  }

  session.publish(SocketMessage(GameResponseAction.LEFT_CONFIRM.type, LeftGameConfirmResponse(game.id)))
}
