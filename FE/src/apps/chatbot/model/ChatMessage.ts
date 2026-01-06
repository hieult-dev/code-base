export type ChatRole = "user" | "assistant" | "system";

export interface ChatMessage {
    role: ChatRole;
    text: string;
    messageId?: number;
}