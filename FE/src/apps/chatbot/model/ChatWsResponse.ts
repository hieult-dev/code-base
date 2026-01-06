export type ChatWsResponseType =
    | "STATUS"
    | "ASSISTANT_MESSAGE"
    | "ERROR";

export interface ChatWsResponse {
    type: ChatWsResponseType;
    sessionId: string;
    content: string;
    messageId?: number | null;
}