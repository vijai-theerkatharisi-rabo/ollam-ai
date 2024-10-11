package com.vijai.ai.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface GenAIService {

    @SystemMessage(fromResource = "prompt.txt")
    String generateResponse(String question);
}
