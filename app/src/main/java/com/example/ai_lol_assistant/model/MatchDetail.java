package com.example.ai_lol_assistant.model;

import java.util.List;

public class MatchDetail {
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

        public String getSummonerName() {
            return summonerName;
        }

        public int getChampionId() {
            return championId;
        }

        public int getKills() {
            return kills;
        }

        public int getDeaths() {
            return deaths;
        }

        public int getAssists() {
            return assists;
        }

        public boolean isWin() {
            return win;
        }

        public int getTeamId() {
            return teamId;
        }
    }
}
