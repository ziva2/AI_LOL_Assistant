package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.AccountResponse;
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

        // UI 요소 초기화
        tvSummonerName = findViewById(R.id.tvSummonerName);
        tvSummonerInfo = findViewById(R.id.tvSummonerInfo);
        tvMatchData = findViewById(R.id.tvMatchData);

        // Riot API 서비스 초기화
        riotApiService = RiotApiClient.getClient().create(RiotApiService.class);

        // MainActivity에서 전달된 데이터 가져오기
        String summonerName = getIntent().getStringExtra("summonerName");
        String tagLine = getIntent().getStringExtra("tagLine");

        if (summonerName == null || tagLine == null || summonerName.isEmpty() || tagLine.isEmpty()) {
            Toast.makeText(this, "Invalid Summoner Name or Tag Line", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 유저명 표시
        tvSummonerName.setText(summonerName + "#" + tagLine);

        // Riot API 호출
        fetchAccountData(summonerName, tagLine);
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
                                        "소환사명: " + account.getGameName() + "\n" +
                                                "Tag Line: " + account.getTagLine()
                                );
                                // Fetch additional data
                                fetchMatchIds(account.getPuuid());
                            } else {
                                Toast.makeText(ResultActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
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

    private void fetchMatchIds(String puuid) {
        riotApiService.getMatchIds(puuid, 0, 10)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<String> matchIds = response.body();
                            StringBuilder matchInfo = new StringBuilder("Match IDs:\n");
                            for (String matchId : matchIds) {
                                matchInfo.append(matchId).append("\n");
                            }
                            tvMatchData.setText(matchInfo.toString());
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
}