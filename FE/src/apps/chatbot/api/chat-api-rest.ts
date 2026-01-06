import baseApi from "../../../common/auth/api/rest/baseApi";
import { CHAT_URL } from "../../../common/config/api";
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