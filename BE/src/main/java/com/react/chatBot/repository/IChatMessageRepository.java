package com.react.chatBot.repository;

import com.react.chatBot.entity.ChatMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IChatMessageRepository extends JpaRepository<ChatMessages, Long> {
    List<ChatMessages> findBySession_IdOrderByCreatedAtAsc(Long sessionId);

    // ✅ lấy 20 message gần nhất (desc)
    List<ChatMessages> findTop20BySession_IdOrderByCreatedAtDesc(Long sessionId);

    @Query("""
    select m
    from ChatMessages m
    join m.session s
    where s.sessionKey = :sessionKey
    order by m.createdAt asc
""")
    List<ChatMessages> findBySessionKeyOrderByCreatedAtAsc(
            @Param("sessionKey") String sessionKey
    );
}
