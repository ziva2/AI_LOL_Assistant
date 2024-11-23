package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.AccountResponse;
import com.example.ai_lol_assistant.model.ChampionMastery;
import com.example.ai_lol_assistant.model.MatchDetail;
import com.example.ai_lol_assistant.network.RiotApiClient;
import com.example.ai_lol_assistant.network.RiotApiService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private TextView tvSummonerName;
    private TextView tvSummonerInfo;
    private TextView tvChampionMastery;
    private TextView tvMatchData;
    private RiotApiService riotApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvSummonerName = findViewById(R.id.tvSummonerName);
        tvSummonerInfo = findViewById(R.id.tvSummonerInfo);
        tvChampionMastery = findViewById(R.id.tvChampionMastery);
        tvMatchData = findViewById(R.id.tvMatchData);

        // Riot API 서비스 초기화
        riotApiService = RiotApiClient.getClient().create(RiotApiService.class);

        // Riot ID (유저명#태그라인)
        String userName = "midoespio";
        String tagLine = "KR1";

        // 유저명 표시
        tvSummonerName.setText(userName + "#" + tagLine);

        // API 호출
        fetchAccountData(userName, tagLine);
    }

    private void fetchAccountData(String userName, String tagLine) {
        try {
            // URL 인코딩
            String encodedUserName = URLEncoder.encode(userName, StandardCharsets.UTF_8.toString());

            riotApiService.getAccountByRiotId(encodedUserName, tagLine)
                    .enqueue(new Callback<AccountResponse>() {
                        @Override
                        public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                AccountResponse account = response.body();
                                tvSummonerInfo.setText(
                                        "Game Name: " + account.getGameName() + "\n" +
                                                "Tag Line: " + account.getTagLine()
                                );
                                fetchChampionMasteries(account.getPuuid());
                                fetchMatchIds(account.getPuuid());
                            } else {
                                Toast.makeText(ResultActivity.this, "Failed to fetch account data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AccountResponse> call, Throwable t) {
                            Toast.makeText(ResultActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchChampionMasteries(String summonerId) {
        riotApiService.getChampionMasteries(summonerId)
                .enqueue(new Callback<List<ChampionMastery>>() {
                    @Override
                    public void onResponse(Call<List<ChampionMastery>> call, Response<List<ChampionMastery>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<ChampionMastery> masteries = response.body();
                            StringBuilder masteryInfo = new StringBuilder();
                            masteryInfo.append("Highest Win Rate Champion:\n");

                            // 가장 높은 승률의 챔피언
                            ChampionMastery highestWinRate = masteries.get(0);
                            masteryInfo.append("Champion ID: ").append(highestWinRate.getChampionId())
                                    .append(", Points: ").append(highestWinRate.getChampionPoints())
                                    .append("\n");

                            tvChampionMastery.setText(masteryInfo.toString());
                        } else {
                            tvChampionMastery.setText("Failed to fetch champion masteries");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ChampionMastery>> call, Throwable t) {
                        tvChampionMastery.setText("Error: " + t.getMessage());
                    }
                });
    }

    private void fetchMatchIds(String puuid) {
        riotApiService.getMatchIds(puuid, 0, 3) // 최근 3경기만 가져옴
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<String> matchIds = response.body();
                            for (String matchId : matchIds) {
                                fetchMatchDetail(matchId);
                            }
                        } else {
                            tvMatchData.setText("Failed to fetch match IDs");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        tvMatchData.setText("Error: " + t.getMessage());
                    }
                });
    }

    private void fetchMatchDetail(String matchId) {
        riotApiService.getMatchDetail(matchId)
                .enqueue(new Callback<MatchDetail>() {
                    @Override
                    public void onResponse(Call<MatchDetail> call, Response<MatchDetail> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MatchDetail matchDetail = response.body();
                            displayMatchData(matchDetail);
                        } else {
                            tvMatchData.append("Failed to fetch details for match: " + matchId + "\n");
                        }
                    }

                    @Override
                    public void onFailure(Call<MatchDetail> call, Throwable t) {
                        tvMatchData.append("Error fetching match details: " + t.getMessage() + "\n");
                    }
                });
    }

    private void displayMatchData(MatchDetail matchDetail) {
        StringBuilder matchInfo = new StringBuilder("Match Details:\n");

        List<MatchDetail.Participant> participants = matchDetail.getParticipants();
        for (MatchDetail.Participant participant : participants) {
            String team = participant.getTeamId() == 100 ? "Blue Team" : "Red Team";
            matchInfo.append(String.format(
                    "Team: %s, Summoner: %s, Champion: %d, KDA: %d/%d/%d\n",
                    team,
                    participant.getSummonerName(),
                    participant.getChampionId(),
                    participant.getKills(),
                    participant.getDeaths(),
                    participant.getAssists()
            ));
        }

        tvMatchData.append(matchInfo.toString());
    }
}
