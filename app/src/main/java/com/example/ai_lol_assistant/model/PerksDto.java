package com.example.ai_lol_assistant.model;

import java.util.List;

public class PerksDto {
    // 필드 정의
    private PerkStatsDto statPerks;         // Perk statistics
    private List<PerkStyleDto> styles;      // List of perk styles

    // Getters
    public PerkStatsDto getStatPerks() {
        return statPerks;
    }

    public List<PerkStyleDto> getStyles() {
        return styles;
    }
}
