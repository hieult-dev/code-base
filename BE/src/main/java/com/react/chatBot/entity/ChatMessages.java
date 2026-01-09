package com.react.chatBot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chat_messages")
public class ChatMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatSession session;

    @Enumerated(EnumType.STRING)
    private MessageRole role;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public static ChatMessages user(ChatSession session, String content) {
        ChatMessages m = new ChatMessages();
        m.session = session;
        m.role = MessageRole.USER;
        m.content = content;
        return m;
    }

    public static ChatMessages assistant(ChatSession session, String content) {
        ChatMessages m = new ChatMessages();
        m.session = session;
        m.role = MessageRole.ASSISTANT;
        m.content = content;
        return m;
    }
}
