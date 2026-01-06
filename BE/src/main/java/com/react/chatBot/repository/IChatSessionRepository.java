package com.react.chatBot.repository;

import com.react.chatBot.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IChatSessionRepository extends JpaRepository<ChatSession, Long> {
    Optional<ChatSession> findBySessionKey(String sessionKey);
    List<ChatSession> findByUserIdOrderByUpdatedAtDesc(Long userId);
}
