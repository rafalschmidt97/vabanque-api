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

data class FinishGameRequest(
  override val gameId: String
) : GameMessagePayload

data class FinishedConfirmGameResponse(
  override val gameId: String,
  val finishedAt: Date
) : GameMessagePayload

data class FinishedWaitGameResponse(
  override val gameId: String,
  val finishedAt: Date
) : GameMessagePayload

fun finishGame(session: WebSocketSession, request: FinishGameRequest) {
  try {
    val (game) = gameAdminAction(session, request)
    game.finish()

    publishGameExcludeSelf(
      SocketMessage(
        GameResponseAction.FINISHED_WAIT.type,
        FinishedWaitGameResponse(game.id, game.currentTime.pausedAt!!)
      ),
      game,
      session
    )

    session.publish(
      SocketMessage(
        GameResponseAction.FINISHED_CONFIRM.type,
        FinishedConfirmGameResponse(game.id, game.currentTime.pausedAt!!)
      )
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_FINISH_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
