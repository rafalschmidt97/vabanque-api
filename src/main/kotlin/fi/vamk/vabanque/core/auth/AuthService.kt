package fi.vamk.vabanque.core.auth

import fi.vamk.vabanque.accounts.Account
import fi.vamk.vabanque.accounts.AccountsRepository
import fi.vamk.vabanque.common.exceptions.ConflictException
import fi.vamk.vabanque.common.exceptions.UnauthorizedException
import fi.vamk.vabanque.common.utilities.getDate
import fi.vamk.vabanque.core.auth.refresh.RefreshToken
import fi.vamk.vabanque.core.auth.refresh.RefreshTokenRepository
import fi.vamk.vabanque.core.auth.token.TokenResponse
import fi.vamk.vabanque.core.auth.token.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
  private val accountsRepository: AccountsRepository,
  private val refreshTokenRepository: RefreshTokenRepository,
  private val tokenService: TokenService,
  private val passwordEncoder: PasswordEncoder) {

  fun signIn(request: SignInRequest): TokenResponse {
    val account = accountsRepository.findByEmail(request.email)
      ?: throw UnauthorizedException()

    if (!passwordEncoder.matches(request.password, account.password)) {
      throw UnauthorizedException()
    }

    val accessToken = tokenService.generateAccess(account.id)
    val refreshToken = generateAndSaveRefreshToken(account.id)

    return TokenResponse(accessToken, refreshToken)
  }

  fun signUp(request: SignUpRequest): TokenResponse {
    if (accountsRepository.existsByEmail(request.email)) {
      throw ConflictException.alreadyExists(Account::class, "email=${request.email}")
    }

    val account = Account(request.email, passwordEncoder.encode(request.password))
    accountsRepository.save(account)

    val accessToken = tokenService.generateAccess(account.id)
    val refreshToken = generateAndSaveRefreshToken(account.id)

    return TokenResponse(accessToken, refreshToken)
  }

  fun refresh(request: RefreshRequest): TokenResponse {
    val token = refreshTokenRepository.findByToken(request.refreshToken)
      ?: throw UnauthorizedException("Refresh token does not exists.")

    if (token.isExpired()) {
      throw UnauthorizedException("Refresh token has expired.")
    }

    val accessToken = tokenService.generateAccess(token.accountId)
    return TokenResponse(accessToken, request.refreshToken)
  }

  fun logout(request: LogoutRequest) {
    refreshTokenRepository.deleteByToken(request.refreshToken)
  }

  fun logoutAllDevices(accountId: Long) {
    refreshTokenRepository.deleteAllByAccountId(accountId)
  }

  private fun generateAndSaveRefreshToken(accountId: Long): String {
    val randomText = tokenService.generateRefresh()
    val twoYearsInFutureDate = getDate(Calendar.YEAR, 2)
    val token = RefreshToken(twoYearsInFutureDate, randomText, accountId)
    refreshTokenRepository.save(token)
    return randomText
  }
}
