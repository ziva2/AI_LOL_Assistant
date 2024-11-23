package com.example.ai_lol_assistant.model;

public class PerkStatsDto {
    // 필드 정의
    private int defense; // Defense-related perk points
    private int flex;    // Flexibility-related perk points
    private int offense; // Offense-related perk points

    // Getters
    public int getDefense() {
        return defense;
    }

    public int getFlex() {
        return flex;
    }

    public int getOffense() {
        return offense;
    }
}