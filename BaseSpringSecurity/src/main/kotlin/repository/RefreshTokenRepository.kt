package com.repository

import com.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Int> {
    fun findByToken(token: String): RefreshToken?
    fun deleteByToken(token: String)
    fun deleteAllByUserId(userId: Int): Int
}