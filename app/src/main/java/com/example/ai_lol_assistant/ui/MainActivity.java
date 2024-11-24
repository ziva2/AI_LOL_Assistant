package com.example.ai_lol_assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.network.RiotApiClient;
import com.example.ai_lol_assistant.network.RiotApiService;
import com.example.ai_lol_assistant.model.AccountDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RiotApiService riotApiService;

    // 로그 태그 정의
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Riot API 초기화
        riotApiService = RiotApiClient.getClient().create(RiotApiService.class);

        // UI 요소 초기화
        EditText etSummonerName = findViewById(R.id.etSummonerName);
        EditText etTagLine = findViewById(R.id.etTagLine);
        Button btnSearch = findViewById(R.id.btnSearch);

        // 검색 버튼 클릭 이벤트
        btnSearch.setOnClickListener(v -> {
            Animation clickAnimation = AnimationUtils.loadAnimation(this, R.anim.click_scale);
            String summonerName = etSummonerName.getText().toString().trim();
            String tagLine = etTagLine.getText().toString().trim();

            if (summonerName.isEmpty() || tagLine.isEmpty()) {
                Log.w(TAG, "Please enter both Summoner Name and Tag Line");
            } else {
                fetchSummonerInfo(summonerName, tagLine);
            }
        });
    }

    private void fetchSummonerInfo(String summonerName, String tagLine) {
        riotApiService.getAccountByRiotId(summonerName, tagLine)
                .enqueue(new Callback<AccountDto>() {
                    @Override
                    public void onResponse(Call<AccountDto> call, Response<AccountDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String puuid = response.body().getPuuid();
                            Log.d(TAG, "Successfully fetched PUUID: " + puuid);
                            fetchMatchIds(puuid, summonerName, tagLine);
                        } else {
                            Log.e(TAG, "Failed to fetch summoner info. Response: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountDto> call, Throwable t) {
                        Log.e(TAG, "Error fetching summoner info: " + t.getMessage(), t);
                    }
                });
    }

    private void fetchMatchIds(String puuid, String summonerName, String tagLine) {
        riotApiService.getMatchIds(puuid, 0, 20)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<String> matchIds = response.body();
                            Log.d(TAG, "Successfully fetched match IDs: " + matchIds);
                            navigateToResultActivity(puuid, summonerName, tagLine, matchIds);
                        } else {
                            Log.e(TAG, "Failed to fetch match IDs. Response: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                        Log.e(TAG, "Error fetching match IDs: " + t.getMessage(), t);
                    }
                });
    }

    private void navigateToResultActivity(String puuid, String summonerName, String tagLine, List<String> matchIds) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("puuid", puuid); // PUUID 전달
        intent.putExtra("summonerName", summonerName); // 소환사 이름 전달
        intent.putExtra("tagLine", tagLine); // 태그라인 전달
        intent.putStringArrayListExtra("matchIds", new ArrayList<>(matchIds)); // Match ID 리스트 전달
        Log.d(TAG, "Navigating to ResultActivity with PUUID: " + puuid + ", Match IDs: " + matchIds);
        startActivity(intent);
    }
}
