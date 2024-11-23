package com.example.ai_lol_assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        EditText etSummonerName = findViewById(R.id.etSummonerName);
        EditText etTagLine = findViewById(R.id.etTagLine);
        Button btnSearch = findViewById(R.id.btnSearch);

        // 검색 버튼 클릭 이벤트
        btnSearch.setOnClickListener(v -> {
            String summonerName = etSummonerName.getText().toString().trim();
            String tagLine = etTagLine.getText().toString().trim();

            if (summonerName.isEmpty() || tagLine.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both Summoner Name and Tag Line", Toast.LENGTH_SHORT).show();
            } else {
                // ResultActivity로 이동하며 데이터 전달
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("summonerName", summonerName);
                intent.putExtra("tagLine", tagLine);
                startActivity(intent);
            }
        });
    }
}
