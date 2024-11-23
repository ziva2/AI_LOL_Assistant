package com.example.ai_lol_assistant.model;

import java.util.List;

public class MetadataDto {
    // 필드 정의
    private String dataVersion;          // Match data version
    private String matchId;              // Match ID
    private List<String> participants;   // List of participant PUUIDs

    // Getters
    public String getDataVersion() {
        return dataVersion;
    }

    public String getMatchId() {
        return matchId;
    }

    public List<String> getParticipants() {
        return participants;
    }
}
