import React, { useState } from "react";
import { useChat } from "../hook/useChat";
import "../style/ChatWidget.css";

export default function ChatWidget({ userId = 1 }) {
  const { sessionId, messages, status, send } = useChat(userId);
  const [open, setOpen] = useState(false);
  const [input, setInput] = useState("");

  const onSend = () => {
    send(input);
    setInput("");
  };

  return (
    <div className="chat-root">
      <button className="chat-fab" onClick={() => setOpen((v) => !v)}>
        💬
      </button>

      {open && (
        <div className="chat-box">
          <div className="chat-header">
            <div>Chat</div>
            <div className="chat-sub">
              {sessionId ? `session: ${sessionId.slice(0, 6)}...` : "creating..."}
            </div>
          </div>

          <div className="chat-body">
            {messages.map((m, i) => (
              <div key={i} className={`chat-msg ${m.role}`}>
                {m.text}
              </div>
            ))}
            {status && <div className="chat-status">{status}...</div>}
          </div>

          <div className="chat-input">
            <input
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && onSend()}
              placeholder="Nhập tin nhắn..."
            />
            <button onClick={onSend} disabled={!sessionId}>
              Send
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
