package com.example.ai_lol_assistant.model;

public class ChatRequest {
    private String model;
    private String prompt;
    private int max_tokens;

    public ChatRequest(String model, String prompt, int maxTokens) {
        this.model = model;
        this.prompt = prompt;
        this.max_tokens = maxTokens;
    }
}
