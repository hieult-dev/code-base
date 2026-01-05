package com.react.chatBot.rag.config;

import com.react.chatBot.rag.util.TextSplitter;
import com.react.course.repository.ICourseRepository;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseIndexer {

    private final ICourseRepository courseRepo;
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;

    private static final int CHUNK_SIZE = 300;
    private static final int OVERLAP  = 60;

    public DatabaseIndexer(
            ICourseRepository courseRepo,
            EmbeddingStore<TextSegment> embeddingStore,
            EmbeddingModel embeddingModel
    ) {
        this.courseRepo = courseRepo;
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
    }

    @PostConstruct
    public void indexDatabase() {

        List<TextSegment> segments = new ArrayList<>();

        courseRepo.findAll().forEach(course -> {

            String title = safe(course.getTitle());
            String desc = normalize(safe(course.getDescription()));

            if (desc.isBlank()) return;

            List<TextSegment> chunks = TextSplitter.split(desc, CHUNK_SIZE, OVERLAP);

            for (int i = 0; i < chunks.size(); i++) {
                String chunkText = """
                        Khóa học: %s
                        Mô tả (phần %d/%d):
                        %s
                        """.formatted(
                        title,
                        i + 1,
                        chunks.size(),
                        chunks.get(i).text()
                );

                segments.add(TextSegment.from(
                        chunkText,
                        Metadata.from(Map.of(
                                "source", "database",
                                "entity", "COURSE",
                                "courseId", String.valueOf(course.getId()),
                                "chunk", String.valueOf(i)
                        ))
                ));
            }
        });

        if (segments.isEmpty()) return;

        embeddingStore.addAll(
                embeddingModel.embedAll(segments).content(),
                segments
        );
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private String normalize(String s) {
        if (s == null) return "";
        return s.replace(". ", ".\n")
                .replace("! ", "!\n")
                .replace("? ", "?\n");
    }
}