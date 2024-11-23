package com.example.ai_lol_assistant.model;
 // 소환사 정보 응답 저장
public class Summoner {
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private int summonerLevel;

    // Getters
    public String getPuuid() {
        return puuid;
    }

    public String getName() {
        return name;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }
}
