import { useEffect, useMemo, useRef, useState } from "react";
import { createSession } from "../api/chat-api-rest";
import { createChatSocket } from "../socket/chat-socket";
import type { ChatWsResponse } from "../model/ChatWsResponse";

type UiMsg = { role: "user" | "assistant" | "system"; text: string };

export function useChat(userId: number) {
    const [sessionId, setSessionId] = useState<string>("");
    const [messages, setMessages] = useState<UiMsg[]>([]);
    const [status, setStatus] = useState<string>("");

    const socketRef = useRef<ReturnType<typeof createChatSocket> | null>(null);

    // init: create session + connect
    useEffect(() => {
        let mounted = true;

        (async () => {
            const sid = await createSession(userId);
            if (!mounted) return;

            setSessionId(sid);

            socketRef.current = createChatSocket(sid, (ws: ChatWsResponse) => {
                if (ws.type === "STATUS") {
                    setStatus(ws.content);
                    return;
                }

                if (ws.type === "ASSISTANT_MESSAGE") {
                    setMessages((prev) => [...prev, { role: "assistant", text: ws.content }]);
                    setStatus("");
                    return;
                }

                if (ws.type === "ERROR") {
                    setMessages((prev) => [...prev, { role: "system", text: "Error: " + ws.content }]);
                    setStatus("");
                }
            });

            socketRef.current.connect();
        })();

        return () => {
            mounted = false;
            socketRef.current?.disconnect();
            socketRef.current = null;
        };
    }, [userId]);

    const send = useMemo(() => {
        return (text: string) => {
            const msg = text.trim();
            if (!msg || !socketRef.current || !sessionId) return;

            setMessages((prev) => [...prev, { role: "user", text: msg }]);
            setStatus("typing");

            socketRef.current.send({ userId, sessionId, message: msg });
        };
    }, [userId, sessionId]);

    return { sessionId, messages, status, send };
}
