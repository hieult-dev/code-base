import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import type { ChatWsRequest } from "../model/ChatWsRequest";
import type { ChatWsResponse } from "../model/ChatWsResponse";
import { CHAT_WS_URL } from "@/common/config/api/index.ts";

export function createChatSocket(
    sessionId: string,
    onMessage: (msg: ChatWsResponse) => void
) {
    const client = new Client({
        webSocketFactory: () => new SockJS(CHAT_WS_URL),
        reconnectDelay: 2000,
        debug: () => { },

        onConnect: () => {
            // subscribe nhận message từ BE
            client.subscribe(`/topic/chat/${sessionId}`, (frame) => {
                onMessage(JSON.parse(frame.body) as ChatWsResponse);
            });
        },
    });

    return {
        connect: () => client.activate(),
        disconnect: () => client.deactivate(),

        send: (req: ChatWsRequest) => {
            client.publish({
                destination: "/app/chat.send",
                body: JSON.stringify(req),
            });
        },
    };
}