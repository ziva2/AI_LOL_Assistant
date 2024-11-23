package com.example.ai_lol_assistant.model;

import java.util.List;

public class PerkStyleDto {
    // 필드 정의
    private String description;                      // Description of the perk style
    private List<PerkStyleSelectionDto> selections;  // List of perk style selections
    private int style;                               // Style ID of the perk

    // Getters
    public String getDescription() {
        return description;
    }

    public List<PerkStyleSelectionDto> getSelections() {
        return selections;
    }

    public int getStyle() {
        return style;
    }
}
