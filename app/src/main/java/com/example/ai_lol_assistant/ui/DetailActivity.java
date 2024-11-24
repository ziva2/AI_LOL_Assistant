package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.ParticipantDto;

public class DetailActivity extends AppCompatActivity {
    private TextView tvChampionName, tvKills, tvDeaths, tvAssists, tvGoldEarned, tvCsScore, tvItems, tvSpells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // UI 요소 초기화
        tvChampionName = findViewById(R.id.tvChampionName);
        tvKills = findViewById(R.id.tvKills);
        tvDeaths = findViewById(R.id.tvDeaths);
        tvAssists = findViewById(R.id.tvAssists);
        tvGoldEarned = findViewById(R.id.tvGoldEarned);
        tvCsScore = findViewById(R.id.tvCsScore);
        tvItems = findViewById(R.id.tvItems);
        tvSpells = findViewById(R.id.tvSpells);

        // Intent로 전달된 데이터 가져오기
        ParticipantDto participant = (ParticipantDto) getIntent().getSerializableExtra("participantData");

        if (participant != null) {
            // 데이터를 UI에 표시
            tvChampionName.setText("Champion: " + participant.getChampionName());
            tvKills.setText("Kills: " + participant.getKills());
            tvDeaths.setText("Deaths: " + participant.getDeaths());
            tvAssists.setText("Assists: " + participant.getAssists());
            tvGoldEarned.setText("Gold Earned: " + participant.getGoldEarned());
            tvCsScore.setText("CS Score: " + (participant.getTotalMinionsKilled() + participant.getNeutralMinionsKilled()));

            // 아이템 정보
            String items = "Items: " +
                    participant.getItem0() + ", " +
                    participant.getItem1() + ", " +
                    participant.getItem2() + ", " +
                    participant.getItem3() + ", " +
                    participant.getItem4() + ", " +
                    participant.getItem5() + ", " +
                    participant.getItem6();
            tvItems.setText(items);

            // 스펠 정보
            String spells = "Spells: " +
                    participant.getSummoner1Id() + ", " +
                    participant.getSummoner2Id();
            tvSpells.setText(spells);
        }
    }
}
