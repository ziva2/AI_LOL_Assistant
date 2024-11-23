package com.example.ai_lol_assistant.model;

import java.util.List;

public class InfoDto {
    // 필드 정의
    private String endOfGameResult;               // Indicates if the game ended in termination
    private long gameCreation;                   // Unix timestamp for game creation
    private long gameDuration;                   // Game duration in milliseconds or seconds
    private long gameEndTimestamp;               // Unix timestamp for when the game ends
    private long gameId;                         // Unique game ID
    private String gameMode;                     // Game mode
    private String gameName;                     // Name of the game
    private long gameStartTimestamp;             // Unix timestamp for game start
    private String gameType;                     // Type of the game
    private String gameVersion;                  // Version of the game
    private int mapId;                           // Map ID
    private List<ParticipantDto> participants;   // List of participants in the game
    private String platformId;                   // Platform where the match was played
    private int queueId;                         // Queue ID
    private List<TeamDto> teams;                 // List of teams in the game
    private String tournamentCode;               // Tournament code

    // Getters
    public String getEndOfGameResult() {
        return endOfGameResult;
    }

    public long getGameCreation() {
        return gameCreation;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public long getGameEndTimestamp() {
        return gameEndTimestamp;
    }

    public long getGameId() {
        return gameId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getGameName() {
        return gameName;
    }

    public long getGameStartTimestamp() {
        return gameStartTimestamp;
    }

    public String getGameType() {
        return gameType;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public int getMapId() {
        return mapId;
    }

    public List<ParticipantDto> getParticipants() {
        return participants;
    }

    public String getPlatformId() {
        return platformId;
    }

    public int getQueueId() {
        return queueId;
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public String getTournamentCode() {
        return tournamentCode;
    }
}
