package org.prd.vertexaidemo;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddigConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModelName;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    //EmbeddingModel bean
    /*@Bean
    public VertexAiTextEmbeddingModel textEmbed004Model() {
        VertexAiEmbeddigConnectionDetails connectionDetails = VertexAiEmbeddigConnectionDetails.builder()
                .withProjectId("my-apiproject-17940")
                .withLocation("us-central1")
                .build();
        VertexAiTextEmbeddingOptions options = VertexAiTextEmbeddingOptions.builder()
                .withModel(VertexAiTextEmbeddingModelName.TEXT_EMBEDDING_004)
                .build();
        return new VertexAiTextEmbeddingModel(connectionDetails, options);
    }*/
}
