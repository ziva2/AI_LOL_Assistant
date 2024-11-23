package com.example.ai_lol_assistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;

public class MainActivity extends AppCompatActivity {
    private EditText etSummonerName;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI 요소 초기화
        etSummonerName = findViewById(R.id.etSummonerName);
        btnSearch = findViewById(R.id.btnSearch);

        // 검색 버튼 클릭 리스너
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String summonerName = etSummonerName.getText().toString().trim();

                if (!summonerName.isEmpty()) {
                    // 소환사 이름을 ResultActivity로 전달
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("summonerName", summonerName);
                    startActivity(intent);
                } else {
                    // 입력이 비어 있을 경우 경고 메시지
                    Toast.makeText(MainActivity.this, "Please enter a summoner name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
