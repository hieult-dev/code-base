package com.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "refresh_tokens")
open class RefreshToken(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Int? = null,

    @Column(nullable = false, unique = true, length = 255)
    open val token: String,

    @Column(name = "user_id", nullable = false)
    open val userId: Int,

    @Column(name = "expiry_at", nullable = false)
    open val expiryAt: Instant,

    @Column(name = "created_at", nullable = false)
    open val createdAt: Instant = Instant.now()
)  {
    constructor() : this(null, "", 0, Instant.now(), Instant.now())
}
