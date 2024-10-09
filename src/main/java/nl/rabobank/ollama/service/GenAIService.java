package nl.rabobank.ollama.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface GenAIService {

    @SystemMessage("Only return relevant information no additional information should be returned")
    String generateResponse(String question);
}
