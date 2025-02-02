package fi.vamk.vabanque.accounts

import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Account(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long,

  @Column(nullable = false, length = 50, unique = true)
  var email: String,

  @Column(nullable = false, length = 60)
  var password: String,

  @Column(length = 50, unique = true)
  var nickname: String? = null,

  @Column(length = 30)
  var phoneNumber: String? = null,

  @Column(length = 2048)
  var avatar: String? = null,

  @Column(nullable = false)
  var createdAt: Date = Date()
) {
  constructor(email: String, password: String) : this(id = 0, email = email, password = password)

  fun update(nickname: String, phoneNumber: String, avatar: String) {
    this.nickname = nickname
    this.phoneNumber = phoneNumber
    this.avatar = avatar
  }

  override fun toString(): String {
    return "Account(id=$id, email='$email', nickname=$nickname, phoneNumber=$phoneNumber, avatar=$avatar)"
  }
}
