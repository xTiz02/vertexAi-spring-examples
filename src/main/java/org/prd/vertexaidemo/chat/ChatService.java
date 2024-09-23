package org.prd.vertexaidemo.chat;

import com.google.cloud.vertexai.VertexAI;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    //private VertexAI vertexAI;
    private final VertexAiGeminiChatModel chatModel;

    public ChatService(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
    }


    //VertexAiGeminiConnectionProperties connectionProperties;
    public String chat( String message){
        ChatResponse response = chatModel.call(new Prompt(message));
        return response.getResult().getOutput().getContent();
    }

    public Flux<ChatResponse> chatFlux(String message){
        return chatModel.stream(new Prompt(message));
    }

}
