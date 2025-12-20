package com.react.auth.service;

import com.react.auth.model.RefreshToken;
import com.react.auth.repository.IRefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final IRefreshTokenRepository repo;
    private final long refreshExpMs;

    public RefreshTokenService(
            IRefreshTokenRepository repo,
            @Value("${app.refresh.exp-ms:300000}") long refreshExpMs
    ) {
        this.repo = repo;
        this.refreshExpMs = refreshExpMs;
    }

    @Transactional
    public RefreshToken create(Integer userId) {
        String token = UUID.randomUUID().toString();
        Instant expiry = Instant.now().plusMillis(refreshExpMs);

        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUserId(userId);
        rt.setExpiryAt(expiry);
        rt.setCreatedAt(Instant.now());

        return repo.save(rt);
    }

    @Transactional
    public RefreshToken verify(RefreshToken rt) {
        if (!rt.getExpiryAt().isAfter(Instant.now())) {
            repo.deleteByToken(rt.getToken());
            throw new IllegalStateException("Refresh token expired");
        }
        return rt;
    }

    @Transactional
    public RefreshToken rotate(String oldToken) {
        RefreshToken current = repo.findByToken(oldToken)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        verify(current);
        repo.deleteByToken(current.getToken());

        return create(current.getUserId());
    }

    @Transactional
    public void revokeByToken(String token) {
        repo.deleteByToken(token);
    }

    @Transactional
    public void revokeAllByUser(Long userId) {
        repo.deleteByAllByUserId(userId);
    }
}
