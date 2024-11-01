package org.prd.vertexaidemo.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
public class RagService {

    private String template = """
            You are helping with programming questions and your answers are short and concise.
            Please use the information in the DOCUMENTS section to provide accurate answers, but act as if you know this information innately.
            If you are unsure, simply state that you do not know.
            DOCUMENTS:
            {documents}
            """;

    private final VectorStore vectorStore;

    private final VertexAiGeminiChatModel chatModel;

    public RagService(VectorStore vectorStore, VertexAiGeminiChatModel chatModel) {
        this.vectorStore = vectorStore;
        this.chatModel = chatModel;
    }

    public String search(String message) {
        List<Document> documents = vectorStore.similaritySearch(message);
        String collect =
                documents.stream().map(Document::getContent).collect(Collectors.joining(System.lineSeparator()));
        Message createdMessage = new SystemPromptTemplate(template).createMessage(Map.of("documents", collect));
        UserMessage userMessage = new UserMessage(message);
        Prompt prompt = new Prompt(List.of(createdMessage, userMessage));
        ChatResponse chatResponse = chatModel.call(prompt);
        log.info("Response: {}", chatResponse);
        return chatResponse.getResult().getOutput().getContent();
    }
}