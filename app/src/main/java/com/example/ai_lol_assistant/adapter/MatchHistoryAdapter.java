package com.example.ai_lol_assistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.MatchDto;
import com.example.ai_lol_assistant.model.ParticipantDto;

import java.util.List;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<MatchDto> matchList;
    private final String puuid;

    public MatchHistoryAdapter(Context context, List<MatchDto> matchList, String puuid) {
        this.context = context;
        this.matchList = matchList;
        this.puuid = puuid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchDto match = matchList.get(position);
        ParticipantDto participant = getPlayerFromMatch(match);

        holder.tvChampion.setText(participant.getChampionName());
        holder.tvKDA.setText(String.format("%d/%d/%d", participant.getKills(), participant.getDeaths(), participant.getAssists()));
        holder.tvWinLose.setText(participant.isWin() ? "승리" : "패배");
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChampion, tvKDA, tvWinLose, tvDetailedEvaluation;
        Button btnMore;
        LinearLayout llDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            tvWinLose = itemView.findViewById(R.id.tvWinLose);
            btnMore = itemView.findViewById(R.id.btnMore);
            llDetails = itemView.findViewById(R.id.llDetails);
            tvDetailedEvaluation = itemView.findViewById(R.id.tvDetailedEvaluation);
        }
    }

    private ParticipantDto getPlayerFromMatch(MatchDto match) {
        for (ParticipantDto player : match.getInfo().getParticipants()) {
            if (player.getPuuid().equals(puuid)) {
                return player;
            }
        }
        return match.getInfo().getParticipants().get(0);
    }
}
