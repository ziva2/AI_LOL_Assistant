package com.example.ai_lol_assistant.model;

import java.util.List;

public class MatchDto {
    private String gameId;
    private List<Participant> participants;

    public String getGameId() {
        return gameId;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public static class Participant {
        private String summonerName;
        private int championId;
        private int kills;
        private int deaths;
        private int assists;
        private boolean win;
        private int teamId; // 팀 ID (100 = 블루 팀, 200 = 레드 팀)

    }
}
