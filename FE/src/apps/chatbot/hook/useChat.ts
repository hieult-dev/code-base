import { useMemo } from "react";
import { useChatStore } from "../store/useChatStore";
import { useChatSocket } from "../socket/useChatSocket";

export function useChat(userId: number) {
  const sessions = useChatStore((s) => s.sessions);
  const activeSessionId = useChatStore((s) => s.activeSessionId);
  const messagesBySessionId = useChatStore((s) => s.messagesBySessionId);
  const statusBySessionId = useChatStore((s) => s.statusBySessionId);

  const refreshSessions = useChatStore((s) => s.refreshSessions);
  const selectSession = useChatStore((s) => s.selectSession);
  const newSession = useChatStore((s) => s.newSession);
  const addUserMessage = useChatStore((s) => s.addUserMessage);

  const { send: sendWs } = useChatSocket(userId);

  const messages = useMemo(() => {
    if (!activeSessionId) return [];
    return messagesBySessionId[activeSessionId] ?? [];
  }, [activeSessionId, messagesBySessionId]);

  const status = useMemo(() => {
    if (!activeSessionId) return "";
    return statusBySessionId[activeSessionId] ?? "";
  }, [activeSessionId, statusBySessionId]);

  const send = useMemo(() => {
    return async (content: string) => {
      const msg = content.trim();
      if (!msg) return;

      // nếu chưa có session thì tạo mới
      let sid = activeSessionId;
      if (!sid) sid = await newSession(userId);

      addUserMessage(sid, msg);
      sendWs(msg);
    };
  }, [userId, activeSessionId, newSession, addUserMessage, sendWs]);

  return {
    sessions,
    sessionId: activeSessionId,
    messages,
    status,
    refreshSessions: () => refreshSessions(userId),
    selectSession: (sid: string) => selectSession(userId, sid),
    newSession: () => newSession(userId),
    send,
  };
}
