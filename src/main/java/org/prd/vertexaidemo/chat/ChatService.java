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

    /*
    * 	@Test
	void textStream() {

		String generationTextFromStream = this.chatModel
			.stream(new Prompt("Explain Bulgaria? Answer in 10 paragraphs."))
			.collectList()
			.block()
			.stream()
			.map(ChatResponse::getResults)
			.flatMap(List::stream)
			.map(Generation::getOutput)
			.map(AssistantMessage::getContent)
			.collect(Collectors.joining());

		// logger.info("" + actorsFilms);
		assertThat(generationTextFromStream).isNotEmpty();
	}

	@Test
	void beanStreamOutputConverterRecords() {

		BeanOutputConverter<ActorsFilmsRecord> outputConverter = new BeanOutputConverter<>(ActorsFilmsRecord.class);

		String format = outputConverter.getFormat();
		String template = """
				Generate the filmography of 5 movies for Tom Hanks.
				Remove the ```json outer brackets.
				{format}
				""";
		PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("format", format));
		Prompt prompt = new Prompt(promptTemplate.createMessage());

		String generationTextFromStream = this.chatModel.stream(prompt)
			.collectList()
			.block()
			.stream()
			.map(ChatResponse::getResults)
			.flatMap(List::stream)
			.map(Generation::getOutput)
			.map(AssistantMessage::getContent)
			.collect(Collectors.joining());

		ActorsFilmsRecord actorsFilms = outputConverter.convert(generationTextFromStream);
		// logger.info("" + actorsFilms);
		assertThat(actorsFilms.actor()).isEqualTo("Tom Hanks");
		assertThat(actorsFilms.movies()).hasSize(5);
	}*/

}