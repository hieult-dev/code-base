package com.react.ai.rag.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.qdrant.QdrantEmbeddingStore;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() throws Exception {

        QdrantClient client = new QdrantClient(
                QdrantGrpcClient.newBuilder("localhost", 6334, false).build()
        );

        String collection = "first-vector-db-react";

        // ✅ delete collection (ignore if not exist)
        try {
            client.deleteCollectionAsync(collection).get();
        } catch (Exception ignored) {}

        // ✅ create collection
        client.createCollectionAsync(
                collection,
                Collections.VectorParams.newBuilder()
                        .setSize(384)
                        .setDistance(Collections.Distance.Cosine)
                        .build()
        ).get();

        return QdrantEmbeddingStore.builder()
                .client(client)
                .collectionName(collection)
                .build();
    }
}
