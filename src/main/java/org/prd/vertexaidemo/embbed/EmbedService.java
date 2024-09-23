package org.prd.vertexaidemo.embbed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmbedService {

    //Ya hay un EmbeddingModel en el contexto de la aplicación por defecto, por eso el qualifier
    private final VertexAiTextEmbeddingModel ems;

    public EmbedService(VertexAiTextEmbeddingModel ems) {
        this.ems = ems;
    }

    public List<Embedding> getEmbedding(String prompt) {
        EmbeddingResponse response = ems.embedForResponse(List.of(prompt));
        List<Embedding> embeddings = response.getResults();
        log.info("Embeddings: {}", embeddings);
        return embeddings;
    }
}
