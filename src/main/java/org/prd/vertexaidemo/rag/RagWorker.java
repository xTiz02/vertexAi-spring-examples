package org.prd.vertexaidemo.rag;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RagWorker {


    @Value("classpath:/static/files/PrdImportsa.pdf")
    private Resource pdfResource;

    private final VectorStore vectorStore;

    private final JdbcClient jdbcClient;

    public RagWorker(@Qualifier("pgVectorStore") VectorStore vectorStore, @Qualifier("jdbcCliVectorStore") JdbcClient jdbcClient) {
        this.vectorStore = vectorStore;
        this.jdbcClient = jdbcClient;
    }


    @PostConstruct
    public void init() {
        String sql = "SELECT count(*) FROM vector_store WHERE metadata->>'file_name' = :source";

        Integer count = jdbcClient.sql(sql).
                param("source", pdfResource.getFilename()).
                query(Integer.class).
                single();
        if (count > 0) {
            log.info("Vector already exists for {}", pdfResource.getFilename());
            return;
        }

        DocumentReader reader = new PagePdfDocumentReader(
                pdfResource,
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                .withNumberOfBottomTextLinesToDelete(3)//eliminar las 3 ultimas lineas de texto de cada pagina  (footer)
                                .withNumberOfTopPagesToSkipBeforeDelete(1) //saltar la primera pagina
                                .build())
                        .withPagesPerDocument(1)//cada pagina es un documento
                        .build());
        var textSplitter = new TokenTextSplitter();//separar el texto de los documentos en tokens (palabras) para crear vectores
        //agregar metadata a los documentos
        List<Document> documents = reader.get().stream().peek(document -> {
            document.getMetadata().put("characters", document.getContent().length());
        }).toList();
        this.vectorStore.accept(textSplitter.apply(documents));
        log.info("Vector pdf data created for {}", pdfResource.getFilename());
    }
}
