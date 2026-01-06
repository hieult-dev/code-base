import { useEffect, useRef, useCallback } from "react";
import { createChatSocket } from "../config/chat-socket";
import { useChatStore } from "../store/useChatStore";
import type { ChatWsResponse } from "../model/ChatWsResponse";
import type { ChatWsRequest } from "../model/ChatWsRequest";

export function useChatSocket(userId: number) {
  const activeSessionId = useChatStore((s) => s.activeSessionId);
  const addAssistantMessage = useChatStore((s) => s.addAssistantMessage);
  const setStatus = useChatStore((s) => s.setStatus);

  const socketRef = useRef<ReturnType<typeof createChatSocket> | null>(null);

  useEffect(() => {
    if (!activeSessionId) return;

    // đổi conversation => disconnect socket cũ
    socketRef.current?.disconnect();

    // connect socket mới theo active session
    socketRef.current = createChatSocket(activeSessionId, (ws: ChatWsResponse) => {
      if (ws.type === "STATUS") {
        setStatus(activeSessionId, ws.content);
        return;
      }

      if (ws.type === "ASSISTANT_MESSAGE") {
        addAssistantMessage(activeSessionId, {
          role: "assistant",
          content: ws.content,
          id: ws.messageId ?? undefined,
          createdAt: new Date().toISOString(),
        });
        setStatus(activeSessionId, null);
        return;
      }

      if (ws.type === "ERROR") {
        setStatus(activeSessionId, "error: " + ws.content);
      }
    });

    socketRef.current.connect();

    return () => {
      socketRef.current?.disconnect();
      socketRef.current = null;
    };
  }, [activeSessionId, addAssistantMessage, setStatus]);

  const send = useCallback(
    (message: string) => {
      if (!activeSessionId || !socketRef.current) return;

      const payload: ChatWsRequest = {
        userId,
        sessionId: activeSessionId,
        message,
      };

      socketRef.current.send(payload);
    },
    [userId, activeSessionId]
  );

  return { send };
}
