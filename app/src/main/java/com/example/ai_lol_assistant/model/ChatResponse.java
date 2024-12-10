package com.example.ai_lol_assistant.model;

import java.util.List;

public class ChatResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private String text;

        public String getText() {
            return text;
        }
    }
}
