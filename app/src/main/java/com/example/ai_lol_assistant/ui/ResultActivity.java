package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.adapter.MatchDataAdapter;
import com.example.ai_lol_assistant.model.Match;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView tvSummonerName;
    private RecyclerView rvMatchData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvSummonerName = findViewById(R.id.tvSummonerName);
        rvMatchData = findViewById(R.id.rvMatchData);

        // Get summoner name from MainActivity
        String summonerName = getIntent().getStringExtra("summonerName");
        tvSummonerName.setText("Summoner: " + summonerName);

        // Initialize RecyclerView
        rvMatchData.setLayoutManager(new LinearLayoutManager(this));
        rvMatchData.setAdapter(new MatchDataAdapter(getDummyMatchData()));
    }

    private List<Match> getDummyMatchData() {
        List<Match> matches = new ArrayList<>();
        matches.add(new Match("Ahri", "Win", 10, 2, 8));
        matches.add(new Match("Lee Sin", "Loss", 5, 7, 3));
        matches.add(new Match("Jinx", "Win", 15, 1, 10));
        return matches;
    }
}
