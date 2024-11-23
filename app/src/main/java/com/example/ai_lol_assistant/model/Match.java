package com.example.ai_lol_assistant.model;

//각 경기 데이터 저장

public class Match {
    private String championName;
    private String result; // Win or Loss
    private int kills;
    private int deaths;
    private int assists;

    public Match(String championName, String result, int kills, int deaths, int assists) {
        this.championName = championName;
        this.result = result;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
    }

    // Getters
    public String getChampionName() {
        return championName;
    }

    public String getResult() {
        return result;
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
}
