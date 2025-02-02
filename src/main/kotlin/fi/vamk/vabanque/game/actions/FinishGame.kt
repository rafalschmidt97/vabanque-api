package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.accounts.AccountsService
import fi.vamk.vabanque.common.exceptions.BadRequestException
import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.CustomException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.debtors.DebtorsService
import fi.vamk.vabanque.debtors.RankedDebtorResponse
import fi.vamk.vabanque.debtors.toRankedResponse
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.publishGame
import org.springframework.http.HttpStatus
import org.springframework.web.socket.WebSocketSession

data class FinishGameRequest(
  override val gameId: String,
  val rankedAccountsId: List<Long>
) : GameMessagePayload

data class FinishGameResponse(
  override val gameId: String,
  val debtors: List<RankedDebtorResponse>
) : GameMessagePayload

fun finishGame(
  session: WebSocketSession,
  request: FinishGameRequest,
  accountsService: AccountsService,
  debtorsService: DebtorsService
) {
  try {
    val (game) = gameAdminAction(session, request)

    if (
      request.rankedAccountsId.size != game.players.size ||
      !game.players.map { it.accountId }.containsAll(request.rankedAccountsId)
    ) {
      throw BadRequestException("Ranked accounts does not match players.")
    }

    if (!accountsService.existsRange(request.rankedAccountsId)) {
      throw NotFoundException("One of ${Account::class.simpleName!!}(${request.rankedAccountsId}) is does not exists.")
    }

    val debtors = game.finish(request.rankedAccountsId)
    debtorsService.addRange(debtors)
    GameState.games.remove(game.id)

    publishGame(
      SocketMessage(
        GameResponseAction.FINISHED.type,
        FinishGameResponse(game.id, debtors.map { it.toRankedResponse() })
      ),
      game
    )
  } catch (e: CustomException) {
    throw ConflictException("GAME_FINISH_FAILED", e.message ?: HttpStatus.CONFLICT.reasonPhrase)
  }
}
