package com.example.ai_lol_assistant.model;

import java.util.List;

public class TeamDto {
    // 필드 정의
    private List<BanDto> bans;         // List of banned champions
    private ObjectivesDto objectives; // Team objectives
    private int teamId;               // ID of the team (e.g., 100 for Blue, 200 for Red)
    private boolean win;              // Whether the team won the match

    // Getters
    public List<BanDto> getBans() {
        return bans;
    }

    public ObjectivesDto getObjectives() {
        return objectives;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isWin() {
        return win;
    }
}
