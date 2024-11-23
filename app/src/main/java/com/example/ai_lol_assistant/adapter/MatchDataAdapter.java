package com.example.ai_lol_assistant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.Match;
import java.util.List;

public class MatchDataAdapter extends RecyclerView.Adapter<MatchDataAdapter.MatchViewHolder> {
    private List<Match> matchList;

    public MatchDataAdapter(List<Match> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matchList.get(position);
        holder.tvChampionName.setText(match.getChampionName());
        holder.tvResult.setText(match.getResult());
        holder.tvKDA.setText(String.format("KDA: %d/%d/%d", match.getKills(), match.getDeaths(), match.getAssists()));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView tvChampionName, tvResult, tvKDA;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampionName = itemView.findViewById(R.id.tvChampionName);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvKDA = itemView.findViewById(R.id.tvKDA);
        }
    }
}
