package fi.vamk.vabanque.game.actions

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.accounts.AccountsService
import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.NotFoundException
import fi.vamk.vabanque.core.socket.domain.SocketMessage
import fi.vamk.vabanque.debtors.DebtorsService
import fi.vamk.vabanque.debtors.RankedDebtorResponse
import fi.vamk.vabanque.debtors.toRankedResponse
import fi.vamk.vabanque.game.GameResponseAction
import fi.vamk.vabanque.game.GameState
import fi.vamk.vabanque.game.dto.GameMessagePayload
import fi.vamk.vabanque.game.publishGame
import org.springframework.web.socket.WebSocketSession

data class RankGameRequest(
  override val gameId: String,
  val rankedAccountsId: List<Long>
) : GameMessagePayload

data class RankedGameResponse(
  override val gameId: String,
  val debtors: List<RankedDebtorResponse>
) : GameMessagePayload

fun rankGame(
  session: WebSocketSession,
  request: RankGameRequest,
  accountsService: AccountsService,
  debtorsService: DebtorsService
) {
  val (game) = gameAdminAction(session, request)

  if (
    request.rankedAccountsId.size != game.players.size ||
    !game.players.map { it.accountId }.containsAll(request.rankedAccountsId)
  ) {
    throw ConflictException("Ranked accounts does not match players.")
  }

  if (!accountsService.existsRange(request.rankedAccountsId)) {
    throw NotFoundException("One of ${Account::class.simpleName!!}(${request.rankedAccountsId}) is does not exists.")
  }

  val debtors = game.rank(request.rankedAccountsId)
  debtorsService.addRange(debtors)
  GameState.games.remove(game.id)

  publishGame(
    SocketMessage(
      GameResponseAction.RANKED.type,
      RankedGameResponse(game.id, debtors.map { it.toRankedResponse() })
    ),
    game
  )
}
