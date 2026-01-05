package com.react.chatBot.rag.service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RetrieverService {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    private static final double MIN_SCORE = 0.65;

    public RetrieverService(
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    private List<EmbeddingMatch<TextSegment>> search(String query, int topK) {

        if (query == null || query.isBlank() || topK <= 0) {
            return List.of();
        }

        Embedding embedding = embeddingModel.embed(query).content();

        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .maxResults(topK)
                .minScore(MIN_SCORE)
                .build();

        return embeddingStore.search(request).matches();
    }

    public String retrieveAsContext(String query, int topK) {
        return search(query, topK).stream()
                .map(m -> m.embedded().text())
                .collect(Collectors.joining("\n---\n"));
    }
}
