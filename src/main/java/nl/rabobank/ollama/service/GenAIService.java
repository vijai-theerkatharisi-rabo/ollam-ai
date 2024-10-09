package nl.rabobank.ollama.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface GenAIService {

    @SystemMessage("Return exact syntax with full formatted response")
    String generateResponse(String question);
}
