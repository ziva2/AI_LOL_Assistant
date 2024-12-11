package com.example.ai_lol_assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.adapter.MatchHistoryAdapter;
import com.example.ai_lol_assistant.model.MatchDto;
import com.example.ai_lol_assistant.network.RiotApiClient;
import com.example.ai_lol_assistant.network.RiotApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = "ResultActivity";
    private RiotApiService riotApiService;
    private MatchHistoryAdapter matchHistoryAdapter;
    private List<MatchDto> matchDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UI 요소 초기화
        RecyclerView rvMatchHistory = findViewById(R.id.rvMatchHistory);
        Button btnChat = findViewById(R.id.btnChat);
        TextView tvSummonerName = findViewById(R.id.tvSummonerName);
        TextView tvSummonerInfo = findViewById(R.id.tvSummonerInfo);

        // Riot API 서비스 초기화
        riotApiService = RiotApiClient.getClient().create(RiotApiService.class);

        // MainActivity에서 전달된 데이터 가져오기
        String summonerName = getIntent().getStringExtra("summonerName");
        String tagLine = getIntent().getStringExtra("tagLine");
        List<String> matchIds = getIntent().getStringArrayListExtra("matchIds");
        String puuid = getIntent().getStringExtra("puuid");


        if (summonerName == null || tagLine == null || matchIds == null || puuid == null) {
            Log.e(TAG, "Invalid data received.");
            finish();
            return;
        }

        // 소환사 이름 설정
        setTitle(summonerName + "#" + tagLine);

        tvSummonerName.setText(summonerName);
        tvSummonerInfo.setText(tagLine);

        // 매치 데이터 가져오기
        fetchAllMatchData(matchIds, puuid);

        // 채팅 버튼 클릭 시 ChatActivity로 이동
        btnChat.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        // RecyclerView 설정
        rvMatchHistory.setLayoutManager(new LinearLayoutManager(this));
        matchHistoryAdapter = new MatchHistoryAdapter(this, matchDataList, puuid);
        rvMatchHistory.setAdapter(matchHistoryAdapter);
    }

    private void fetchAllMatchData(List<String> matchIds, String puuid) {
        for (String matchId : matchIds) {
            riotApiService.getMatchById(matchId)
                    .enqueue(new Callback<MatchDto>() {
                        @Override
                        public void onResponse(Call<MatchDto> call, Response<MatchDto> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                matchDataList.add(response.body());
                                Log.d(TAG, "Match fetched: " + response.body().getInfo().getGameCreation());

                                // 모든 매치 데이터를 가져온 후 정렬 및 출력
                                if (matchDataList.size() == matchIds.size()) {
                                    Log.d(TAG, "All matches fetched. Sorting data...");
                                    sortAndNotifyAdapter();
                                }
                            } else {
                                Log.e(TAG, "Failed to fetch match data for matchId: " + matchId + ". Response: " + response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchDto> call, Throwable t) {
                            Log.e(TAG, "Error fetching match data for matchId: " + matchId, t);
                        }
                    });
        }
    }

    private void sortAndNotifyAdapter() {
        // 최신 순 정렬
        Collections.sort(matchDataList, (match1, match2) ->
                Long.compare(match2.getInfo().getGameCreation(), match1.getInfo().getGameCreation()));

        Log.d(TAG, "Matches sorted by creation time (recent first).");
        matchHistoryAdapter.notifyDataSetChanged();
    }
}
