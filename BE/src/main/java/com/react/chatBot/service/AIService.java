package com.react.chatBot.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;


public interface AIService {

    @SystemMessage("""
Bạn là trợ lý kỹ thuật.
CHỈ được trả lời dựa trên CONTEXT bên dưới.

CONTEXT:
{{context}}

Nếu CONTEXT rỗng hoặc không đủ thông tin,
hãy trả lời đúng câu: "Xin lỗi câu hỏi của bạn nằm ngoài tầm hiểu biết của tôi!".
KHÔNG suy đoán, KHÔNG bịa.
""")
    String chat(
            @UserMessage String question,
            @V("context") String context
    );
}
