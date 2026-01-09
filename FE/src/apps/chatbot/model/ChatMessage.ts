export type MessageRole = "user" | "assistant" | "system";

export interface ChatMessage {
    role: MessageRole;
    content: string;
    id?: number;
    clientId?: string;
    createdAt?: string;
}
