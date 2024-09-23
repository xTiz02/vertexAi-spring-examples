package org.prd.vertexaidemo.function;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherChatService {

    private final VertexAiGeminiChatModel chatModel;

    public WeatherChatService(VertexAiGeminiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String query) {
        System.out.println("Model: " + chatModel.getDefaultOptions().getModel());
        ChatResponse response = chatModel.call(new Prompt(
                query,
                VertexAiGeminiChatOptions.builder().withFunction("currentWeatherFunction").build()));
        return response.getResult().getOutput().getContent();
    }
}
