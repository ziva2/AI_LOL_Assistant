package com.example.ai_lol_assistant.model;

public class BanDto {
    // 필드 정의
    private int championId; // ID of the banned champion
    private int pickTurn;   // The turn in which the champion was banned

    // Getters
    public int getChampionId() {
        return championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }
}
