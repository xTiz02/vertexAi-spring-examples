package org.prd.vertexaidemo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddigConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModelName;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;

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
    private DataSource configureDataSource(String schema) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("org.postgresql.Driver")
                .url(String.format("jdbc:postgresql://localhost:5432/%s", schema))
                .username("user")
                .password("pass")
                .build();
    }
    @Bean(name = "dsVector")
    public DataSource dataSourceVector() {
        return configureDataSource("vector");
    }

    @Bean(name = "jdbcCliVectorStore")
    public JdbcClient vectorJdbcClient(@Qualifier("dsVector") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }



    @Bean(name = "pgVectorStore")
    public VectorStore pgVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel) {
        return createPgVectorTable(jdbcTemplate, embeddingModel, "vector_store");
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("dsVector") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private VectorStore createPgVectorTable(JdbcTemplate jdbcTemplate, EmbeddingModel embeddingModel, String tableName) {
        return new PgVectorStore(
                tableName,
                jdbcTemplate, embeddingModel,
                768, PgVectorStore.PgDistanceType.COSINE_DISTANCE,
                false, PgVectorStore.PgIndexType.HNSW,
                false);
    }
}
