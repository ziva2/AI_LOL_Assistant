package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;

public class ResultActivity extends AppCompatActivity {
    private TextView tvSummonerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 소환사 이름 표시
        tvSummonerName = findViewById(R.id.tvSummonerName);

        // MainActivity에서 전달된 데이터 가져오기
        String summonerName = getIntent().getStringExtra("summonerName");
        tvSummonerName.setText("Summoner Name: " + summonerName);
    }
}
