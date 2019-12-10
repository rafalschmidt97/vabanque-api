package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.core.socket.publish
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.publishGameExcludeSelf
import java.util.Date
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class RankGameRequest(
  override val gameId: String
) : GameMessagePayload

data class RankedConfirmGameResponse(
  override val gameId: String,
  val finishedAt: Date
) : GameMessagePayload

data class RankedWaitGameResponse(
  override val gameId: String,
  val finishedAt: Date
) : GameMessagePayload

fun rankGame(session: WebSocketSession, request: RankGameRequest) {
  try {
    val (game) = gameAdminAction(session, request)
    game.rank()

    publishGameExcludeSelf(
      SocketMessage(
        GameResponseAction.RANKED_WAIT.type,
        RankedWaitGameResponse(game.id, game.currentTime.pausedAt!!)
      ),
      game,
      session
    )

    session.publish(
      SocketMessage(
        GameResponseAction.RANKED_CONFIRM.type,
        RankedConfirmGameResponse(game.id, game.currentTime.pausedAt!!)
      )
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_RANK_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
