package com.react.chatBot.rag.util;

import dev.langchain4j.data.segment.TextSegment;

import java.util.ArrayList;
import java.util.List;

public class TextSplitter {

    public static List<TextSegment> split(
            String text,
            int chunkSize,
            int overlap
    ) {
        List<TextSegment> segments = new ArrayList<>();

        int start = 0;
        int length = text.length();

        while (start < length) {
            int end = Math.min(start + chunkSize, length);

            String chunk = text.substring(start, end).trim();
            if (!chunk.isEmpty()) {
                segments.add(TextSegment.from(chunk));
            }

            start += (chunkSize - overlap);
        }

        return segments;
    }
}
