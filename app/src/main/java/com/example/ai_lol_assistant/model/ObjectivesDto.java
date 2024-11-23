package com.example.ai_lol_assistant.model;

public class ObjectivesDto {
    // 필드 정의
    private ObjectiveDto baron;       // Baron-related objectives
    private ObjectiveDto champion;    // Champion-related objectives
    private ObjectiveDto dragon;      // Dragon-related objectives
    private ObjectiveDto horde;       // Horde-related objectives
    private ObjectiveDto inhibitor;   // Inhibitor-related objectives
    private ObjectiveDto riftHerald;  // Rift Herald-related objectives
    private ObjectiveDto tower;       // Tower-related objectives

    // Getters
    public ObjectiveDto getBaron() {
        return baron;
    }

    public ObjectiveDto getChampion() {
        return champion;
    }

    public ObjectiveDto getDragon() {
        return dragon;
    }

    public ObjectiveDto getHorde() {
        return horde;
    }

    public ObjectiveDto getInhibitor() {
        return inhibitor;
    }

    public ObjectiveDto getRiftHerald() {
        return riftHerald;
    }

    public ObjectiveDto getTower() {
        return tower;
    }
}

