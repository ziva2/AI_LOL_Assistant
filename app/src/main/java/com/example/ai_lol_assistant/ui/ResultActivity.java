package com.example.ai_lol_assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.MatchDto;
import com.example.ai_lol_assistant.model.ParticipantDto;
import com.example.ai_lol_assistant.network.RiotApiClient;
import com.example.ai_lol_assistant.network.RiotApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
    private TextView tvSummonerName;
    private TextView tvSummonerInfo;
    private TableLayout tableLayout;
    private RiotApiService riotApiService;
    private List<MatchDto> matchDataList = new ArrayList<>();

    // 로그 태그
    private static final String TAG = "ResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UI 요소 초기화
        tvSummonerName = findViewById(R.id.tvSummonerName);
        tvSummonerInfo = findViewById(R.id.tvSummonerInfo);
        tableLayout = findViewById(R.id.tableLayout);

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
        tvSummonerName.setText(summonerName + "#" + tagLine);

        // 매치 데이터 가져오기
        fetchAllMatchData(matchIds, puuid);

        // 버튼 초기화
        RelativeLayout chatButton = findViewById(R.id.chat_two_bu);

        // 버튼 클릭 시 ChatActivity로 이동
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, ChatActivity.class);
            startActivity(intent);
        });

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
                                    sortAndDisplayMatchData(puuid);
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

    private void fetchMatchData(String matchId, String puuid) {
        riotApiService.getMatchById(matchId)
                .enqueue(new Callback<MatchDto>() {
                    @Override
                    public void onResponse(Call<MatchDto> call, Response<MatchDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            MatchDto match = response.body();
                            Log.d(TAG, "Successfully fetched match data for matchId: " + matchId);
                            displayMatchData(match, puuid);
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

    private void sortAndDisplayMatchData(String puuid) {
        // 최신 순 정렬
        Collections.sort(matchDataList, (match1, match2) ->
                Long.compare(match2.getInfo().getGameCreation(), match1.getInfo().getGameCreation()));

        Log.d(TAG, "Matches sorted by creation time (recent first).");

        // 테이블 데이터 표시
        tableLayout.removeAllViews(); // 기존 데이터 초기화
        for (MatchDto match : matchDataList) {
            displayMatchData(match, puuid);
        }
    }


    private void displayMatchData(MatchDto match, String puuid) {
        for (ParticipantDto participant : match.getInfo().getParticipants()) {
            if (participant.getPuuid().equals(puuid)) {
                TableRow row = new TableRow(this);

                // 챔피언 이름
                TextView tvChampion = new TextView(this);
                tvChampion.setText(participant.getChampionName());
                tvChampion.setPadding(16, 16, 16, 16);
                row.addView(tvChampion);

                // Kills
                TextView tvKills = new TextView(this);
                tvKills.setText(String.valueOf(participant.getKills()));
                tvKills.setPadding(16, 16, 16, 16);
                row.addView(tvKills);

                // Deaths
                TextView tvDeaths = new TextView(this);
                tvDeaths.setText(String.valueOf(participant.getDeaths()));
                tvDeaths.setPadding(16, 16, 16, 16);
                row.addView(tvDeaths);

                // Assists
                TextView tvAssists = new TextView(this);
                tvAssists.setText(String.valueOf(participant.getAssists()));
                tvAssists.setPadding(16, 16, 16, 16);
                row.addView(tvAssists);

                // 테이블에 행 추가
                tableLayout.addView(row);

                // 로그 출력
                Log.d(TAG, "Displayed match data: Champion=" + participant.getChampionName()
                        + ", Kills=" + participant.getKills()
                        + ", Deaths=" + participant.getDeaths()
                        + ", Assists=" + participant.getAssists());
                break;
            }
        }
    }
}
