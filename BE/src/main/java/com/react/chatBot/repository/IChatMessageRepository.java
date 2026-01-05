package com.react.chatBot.repository;

import com.react.chatBot.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IChatMessageRepository extends JpaRepository<ChatMessages, Long> {
    List<ChatMessages> findBySession_IdOrderByCreatedAtAsc(Long sessionId);
}
