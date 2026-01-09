import { create } from "zustand";
import type { ChatSession } from "../model/ChatSession";
import type { ChatMessage } from "../model/ChatMessage";
import {
  listSessions,
  getConversationMessages,
  createSession,
} from "../api/chat-api-rest";

type ChatState = {
  sessions: ChatSession[];
  activeSessionId: string | null;

  messagesBySessionId: Record<string, ChatMessage[]>;
  loaded: Record<string, boolean>;
  statusBySessionId: Record<string, string | null>;

  refreshSessions: (userId: number) => Promise<void>;
  selectSession: (userId: number, sessionId: string) => Promise<void>;
  newSession: (userId: number) => Promise<string>;

  addUserMessage: (sessionId: string, content: string) => void;
  addAssistantMessage: (sessionId: string, msg: ChatMessage) => void;
  setStatus: (sessionId: string, status: string | null) => void;
};

export const useChatStore = create<ChatState>((set, get) => ({
  sessions: [],
  activeSessionId: null,
  messagesBySessionId: {},
  loaded: {},
  statusBySessionId: {},

  refreshSessions: async (userId) => {
    const sessions = await listSessions(userId);
    set({ sessions });

    // auto chọn conversation mới nhất
    if (!get().activeSessionId && sessions.length) {
      await get().selectSession(userId, sessions[0].sessionId);
    }
  },

  selectSession: async (userId, sessionId) => {
    set({ activeSessionId: sessionId });

    if (get().loaded[sessionId]) return;

    const msgs = await getConversationMessages(userId, sessionId);
    set((st) => ({
      messagesBySessionId: {
        ...st.messagesBySessionId,
        [sessionId]: msgs,
      },
      loaded: { ...st.loaded, [sessionId]: true },
    }));
  },

  newSession: async (userId) => {
    const sid = await createSession(userId);

    const newItem: ChatSession = {
      sessionId: sid,
      updatedAt: new Date().toISOString(),
    };

    set((st) => ({
      sessions: [newItem, ...st.sessions],
      activeSessionId: sid,
      messagesBySessionId: { ...st.messagesBySessionId, [sid]: [] },
      loaded: { ...st.loaded, [sid]: true },
    }));

    return sid;
  },

  addUserMessage: (sessionId, content) => {
    const msg: ChatMessage = {
      role: "user",
      content,
      clientId: crypto.randomUUID(),
      createdAt: new Date().toISOString(),
    };

    set((st) => ({
      messagesBySessionId: {
        ...st.messagesBySessionId,
        [sessionId]: [...(st.messagesBySessionId[sessionId] ?? []), msg],
      },
    }));
  },

  addAssistantMessage: (sessionId, msg) => {
    const incoming = {
      ...msg,
      clientId: msg.clientId ?? crypto.randomUUID(),
    };
    set((st) => ({
      messagesBySessionId: {
        ...st.messagesBySessionId,
        [sessionId]: [...(st.messagesBySessionId[sessionId] ?? []), incoming],
      },
    }));
  },

  setStatus: (sessionId, status) => {
    set((st) => ({
      statusBySessionId: {
        ...st.statusBySessionId,
        [sessionId]: status,
      },
    }));
  },
}));
