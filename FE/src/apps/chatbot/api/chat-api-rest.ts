import baseApi from "../../../common/auth/api/rest/baseApi";
import { CHAT_URL } from "../../../common/config/api";
import { ChatMessage } from "../model/ChatMessage";
import { ChatSession } from "../model/ChatSession";
import { CreateSessionResponse } from "../model/CreateSessionResponse";

export async function createSession(userId: number): Promise<string> {
    const data = await baseApi.post<CreateSessionResponse>(
        `${CHAT_URL}/sessions`,
        {},
        {
            headers: {
                "X-User-Id": userId,
            },
        }
    );

    return data.sessionId;
}
export async function listSessions(userId: number): Promise<ChatSession[]> {
  const data = await baseApi.get<ChatSession[]>(
    `${CHAT_URL}/sessions`,
    { headers: { "X-User-Id": userId } }
  );
  return data;
}

export async function getConversationMessages(
  userId: number,
  sessionId: string
): Promise<ChatMessage[]> {
  return baseApi.get<ChatMessage[]>(
    `${CHAT_URL}/sessions/${sessionId}/messages`,
    { headers: { "X-User-Id": userId } }
  );
}