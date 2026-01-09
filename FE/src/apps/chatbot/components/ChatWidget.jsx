import React, { useEffect, useState, useRef } from "react";
import { useChat } from "../hook/useChat";
import "../style/ChatWidget.css";

export default function ChatWidget({ userId = 1 }) {
  const {
    sessions,
    sessionId,        // activeSessionId
    messages,
    status,
    send,
    refreshSessions,
    selectSession,
    newSession,
  } = useChat(userId);

  const [open, setOpen] = useState(false);
  const [showList, setShowList] = useState(false);
  const [input, setInput] = useState("");
  const bodyRef = useRef(null);

  useEffect(() => {
    if (open) refreshSessions();
  }, [open]);

  useEffect(() => {
    if (!open) return;
    const el = bodyRef.current;
    if (!el) return;
    el.scrollTop = el.scrollHeight;
  }, [open, sessionId, messages.length]);

  const onSend = () => {
    send(input);
    setInput("");
  };

  return (
    <div className="chat-root">
      {!open && (
        <button className="chat-fab" onClick={() => setOpen(true)}>
          💬
        </button>
      )}

      {open && (
        <div className="chat-box">
          {/* HEADER */}
          <div className="chat-header">
            <div className="chat-title">Chatbot-AI (design by HieuLT)</div>

            <div className="chat-header-actions">
              <button
                className="chat-menu"
                onClick={() => setShowList((v) => !v)}
                title="Danh sách hội thoại"
              >
                ≡
              </button>

              <button
                className="chat-close"
                onClick={() => setOpen(false)}
                title="Đóng"
              >
                ✕
              </button>
            </div>
          </div>

          {/* PANEL LIST CONVERSATIONS */}
          {showList && (
            <div className="chat-list">
              <div className="chat-list-title">Cuộc hội thoại gần đây</div>

              <div className="chat-list-items">
                {sessions.length === 0 && (
                  <div className="chat-list-empty">Chưa có hội thoại</div>
                )}

                {sessions.slice(0, 10).map((s) => (
                  <button
                    key={s.sessionId}
                    className={`chat-list-item ${s.sessionId === sessionId ? "active" : ""}`}
                    onClick={() => {
                      selectSession(s.sessionId);
                      setShowList(false);
                    }}
                  >
                    <div className="chat-list-item-title">
                      {`Chat ${s.sessionId.slice(0, 6)}`}
                    </div>
                    <div className="chat-list-item-sub">
                      {new Date(s.updatedAt).toLocaleString()}
                    </div>
                  </button>
                ))}
              </div>

              <div className="chat-list-actions">
                <button className="chat-btn" onClick={() => newSession()}>
                  + New
                </button>
                <button className="chat-btn" onClick={() => alert("làm sau: xem tất cả")}>
                  Xem tất cả
                </button>
              </div>
            </div>
          )}

          {/* BODY */}
          <div className="chat-body" ref={bodyRef}>
            {messages.map((m, i) => (
              <div
                key={m.id != null ? `id-${m.id}` : `idx-${i}`}
                className={`chat-msg ${String(m.role).toLowerCase()}`}
              >
                {m.content}
              </div>
            ))}
            {status && <div className="chat-status">{status}...</div>}
          </div>

          {/* INPUT */}
          <div className="chat-input">
            <textarea
              className="chat-textarea"
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter" && !e.shiftKey) {
                  e.preventDefault();
                  onSend();
                }
              }}
              placeholder="Nhập tin nhắn..."
              rows={1}
            />

            <div className="chat-actions">
              <button
                className="chat-icon-btn chat-send-btn"
                type="button"
                title="Send"
                onClick={onSend}
                disabled={!input.trim() || !sessionId}
              >
                ➤
              </button>
            </div>
          </div>

        </div>
      )}
    </div>
  );
}
