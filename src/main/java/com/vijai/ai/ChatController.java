package com.vijai.ai;

import lombok.RequiredArgsConstructor;
import com.vijai.ai.service.GenAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final GenAIService genAIService;

    @GetMapping("/ask")
    public String askQuestion(@RequestParam("question") String question) {
        return genAIService.generateResponse(question);
    }
}