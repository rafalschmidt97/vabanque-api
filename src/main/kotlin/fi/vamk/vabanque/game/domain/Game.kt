package fi.vamk.vabanque.game.domain

import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.utilities.getRandomAlfaNumeric
import fi.vamk.vabanque.debtors.Debtor
import java.time.LocalTime
import java.util.UUID

data class Game(
  val id: String,
  val code: String,
  val duration: LocalTime,
  val entry: String,
  val progression: MutableList<GameProgression>,
  val players: MutableList<Player>
) {
  var status = GameStatus.IN_LOBBY
    private set

  val time = mutableListOf<GameTime>()

  val currentProgression: GameProgression
    get() = progression[currentProgressionIndex]

  val currentTime: GameTime
    get() = time[currentTimeIndex]

  val previousTime: GameTime?
    get() = if (currentTimeIndex != 0) time[currentTimeIndex - 1] else null

  private var currentProgressionIndex = 0
  private var currentTimeIndex = 0

  constructor(duration: LocalTime, entry: String, progression: MutableList<GameProgression>, adminId: Long) :
    this(
      UUID.randomUUID().toString(),
      getRandomAlfaNumeric(8),
      duration,
      entry,
      progression,
      mutableListOf(Player(adminId, true))
    )

  fun start() {
    if (status != GameStatus.IN_LOBBY) {
      throw ConflictException("GAME_ALREADY_STARTED", "${Game::class.simpleName!!}($id) is already started.")
    }

    if (players.size == 1) {
      throw ConflictException("GAME_REQUIRES_TWO_PLAYERS", "${Game::class.simpleName!!}($id) requires at least two players.")
    }

    status = GameStatus.PLAYING
    time.add(GameTime(currentProgression))
  }

  fun pause() {
    if (status != GameStatus.PLAYING) {
      throw ConflictException("GAME_NOT_PLAYED", "${Game::class.simpleName!!}($id) is not played.")
    }

    currentTime.pause()
    status = GameStatus.PAUSED
  }

  fun resume() {
    if (status != GameStatus.PAUSED) {
      throw ConflictException("GAME_NOT_PAUSED", "${Game::class.simpleName!!} is not paused.")
    }

    time.add(GameTime(currentProgression))
    status = GameStatus.PLAYING
    currentTimeIndex++
  }

  fun raise() {
    if (status != GameStatus.PLAYING) {
      throw ConflictException("GAME_NOT_PLAYED", "${Game::class.simpleName!!}($id) is not played.")
    }

    currentTime.pause()
    currentTimeIndex++

    if (currentProgressionIndex + 1 == progression.size) {
      progression.add(
        GameProgression(
          currentProgression.small * 2,
          currentProgression.big * 2
        )
      )
    }

    currentProgressionIndex++
    time.add(GameTime(currentProgression))
  }

  fun finish() {
    if (status == GameStatus.IN_LOBBY) {
      throw ConflictException("GAME_NOT_STARTED", "${Game::class.simpleName!!} is not started.")
    }

    currentTime.pause()
    status = GameStatus.FINISHED
  }

  fun rank(rankedAccountsId: List<Long>): List<Debtor> {
    if (status != GameStatus.FINISHED) {
      throw ConflictException("GAME_NOT_FINISHED", "${Game::class.simpleName!!} is not finished.")
    }

    val debtors = mutableListOf<Debtor>()

    when (players.size) {
      2 -> {
        debtors.add(Debtor(rankedAccountsId[0], rankedAccountsId[1], entry))
      }
      3 -> {
        debtors.add(Debtor(rankedAccountsId[0], rankedAccountsId[1], entry))
        debtors.add(Debtor(rankedAccountsId[0], rankedAccountsId[2], entry))
      }
      4 -> {
        players.takeLast(players.size - 2).forEach {
          debtors.add(Debtor(rankedAccountsId[0], it.accountId, entry))
        }
      }
      else -> { // 5+
        debtors.add(Debtor(rankedAccountsId[1], rankedAccountsId[2], entry))

        players.takeLast(players.size - 3).forEach {
          debtors.add(Debtor(rankedAccountsId[0], it.accountId, entry))
        }
      }
    }

    return debtors
  }

  override fun toString(): String {
    return "Game(id='$id', duration=$duration, entry='$entry', progression=$progression, players=$players, " +
      "status=$status, time=$time, currentProgressionIndex=$currentProgressionIndex, currentTimeIndex=$currentTimeIndex)"
  }
}
