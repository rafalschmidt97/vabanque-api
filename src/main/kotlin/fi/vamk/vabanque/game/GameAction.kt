package fi.vamk.vabanque.game

enum class GameRequestAction(val type: String) {
  CREATE("create"),
  JOIN("join"),
  LEAVE("leave"),
  SYNC("sync"),
  REMOVE("remove"),
  START("start"),
  PAUSE("pause"),
  RESUME("resume"),
  RAISE("raise"),
  RANK("rank"),
  FINISH("finish"),
}

enum class GameResponseAction(val type: String) {
  CREATED_CONFIRM("created_confirm"),
  JOINED("joined"),
  JOINED_CONFIRM("joined_confirm"),
  LEFT("left"),
  LEFT_CONFIRM("left_confirm"),
  DISCONNECTED("disconnected"),
  RECONNECTED("reconnected"),
  REMOVED("removed"),
  REMOVED_CONFIRM("removed_confirm"),
  SYNC("sync"),
  STARTED("started"),
  PAUSED("paused"),
  RESUMED("resumed"),
  RAISED("raised"),
  RANKED_CONFIRM("ranked_confirm"),
  RANKED_WAIT("ranked_wait"),
  FINISHED("finished"),
}
