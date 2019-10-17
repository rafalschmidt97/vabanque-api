package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.core.socket.SocketMessage
import fi.vamk.vabanque.core.socket.getAccountId
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.publishGame
import fi.vamk.vabanque.game.toResponse
import org.springframework.web.socket.WebSocketSession

fun markInAllAsDisconnectedGame(session: WebSocketSession) {
  val accountId = session.getAccountId()

  val disconnectingFromGames = GameState.games.filterValues { game ->
    game.players.find { player -> player.accountId == accountId } != null
  }

  disconnectingFromGames.values.forEach { disconnectingGame ->
    val disconnectingPlayer = disconnectingGame.players.find { player -> player.accountId == accountId }!!

    if (disconnectingGame.players.size > 1) {
      if (disconnectingPlayer.admin) {
        val nonAdminOtherPlayer = disconnectingGame.players.find { it.accountId != accountId && !it.admin }!!
        nonAdminOtherPlayer.makeAdmin()
        disconnectingPlayer.disconnect()
        publishGame(SocketMessage(GameResponseAction.SYNC.type, disconnectingGame.toResponse()), disconnectingGame)
      } else {
        disconnectingPlayer.disconnect()
        publishGame(SocketMessage(GameResponseAction.DISCONNECTED.type, disconnectingPlayer.toResponse()), disconnectingGame)
      }
    } else {
      GameState.games.remove(disconnectingGame.id)
    }
  }
}
