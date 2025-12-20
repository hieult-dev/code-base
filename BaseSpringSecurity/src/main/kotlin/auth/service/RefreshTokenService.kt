package com.auth.service

import com.repository.RefreshTokenRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.entity.RefreshToken
import java.time.Instant
import java.util.UUID
import jakarta.transaction.Transactional

@Service
open class RefreshTokenService(
    private val repo: RefreshTokenRepository,
    @Value("\${app.refresh.exp-ms:300000}")
    private val refreshExpMs: Long
) {

    @Transactional
    open fun create(userId: Int): RefreshToken {
        val token = UUID.randomUUID().toString()
        val expiry = Instant.now().plusMillis(refreshExpMs)
        return repo.save(RefreshToken(token = token, userId = userId, expiryAt = expiry))
    }

    @Transactional
    open fun verify(rt: RefreshToken): RefreshToken {
        if (!rt.expiryAt.isAfter(Instant.now())) {
            repo.deleteByToken(rt.token)
            throw IllegalStateException("Refresh token expired")
        }
        return rt
    }
    @Transactional
    open fun rotate(oldToken: String): RefreshToken {
        val current = repo.findByToken(oldToken) ?: throw IllegalArgumentException("Invalid token")
        verify(current)
        repo.deleteByToken(current.token)
        return create(current.userId)
    }

    @Transactional
    open fun revokeByToken(token: String) = repo.deleteByToken(token)
    @Transactional
    open fun revokeAllByUser(userId: Int) = repo.deleteAllByUserId(userId)
}