package com.example.ai_lol_assistant.model;

public class ObjectiveDto {
    // 필드 정의
    private boolean first; // Indicates if the objective was taken first
    private int kills;     // Number of kills related to the objective

    // Getter for 'first'
    public boolean isFirst() {
        return first;
    }

    // Getter for 'kills'
    public int getKills() {
        return kills;
    }
}