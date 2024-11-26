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

    private final List<MatchDto> matchList;
    private final String puuid;
    private final Context context;

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
        ParticipantDto participant = findParticipant(match.getInfo().getParticipants(), puuid);

        if (participant != null) {
            // 승패 여부 설정
            holder.tvWinLoss.setText(participant.isWin() ? "승리" : "패배");
            holder.tvWinLoss.setTextColor(participant.isWin() ? 0xFF00FF00 : 0xFFFF0000); // 녹색: 승리, 빨강: 패배

            // 챔피언 이름 설정
            holder.tvChampion.setText(participant.getChampionName());

            // K/D/A 설정
            holder.tvKDA.setText(participant.getKills() + "/" + participant.getDeaths() + "/" + participant.getAssists());

            // 더보기 버튼 클릭 이벤트
            holder.btnMore.setOnClickListener(v -> {
                if (holder.detailContainer.getVisibility() == View.GONE) {
                    holder.detailContainer.setVisibility(View.VISIBLE);
                    holder.tvDetailedInfo.setText(generateDetailedInfo(participant));
                } else {
                    holder.detailContainer.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    private ParticipantDto findParticipant(List<ParticipantDto> participants, String puuid) {
        for (ParticipantDto participant : participants) {
            if (participant.getPuuid().equals(puuid)) {
                return participant;
            }
        }
        return null;
    }

    private String generateDetailedInfo(ParticipantDto participant) {
        return "챔피언: " + participant.getChampionName() + "\n"
                + "KDA: " + participant.getKills() + "/" + participant.getDeaths() + "/" + participant.getAssists() + "\n"
                + "골드: " + participant.getGoldEarned() + "\n"
                + "CS: " + participant.getTotalMinionsKilled();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWinLoss, tvChampion, tvKDA, tvDetailedInfo;
        Button btnMore;
        LinearLayout detailContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWinLoss = itemView.findViewById(R.id.tvWinLoss);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            btnMore = itemView.findViewById(R.id.btnMore);
            detailContainer = itemView.findViewById(R.id.detailContainer);
            tvDetailedInfo = itemView.findViewById(R.id.tvDetailedInfo);
        }
    }
}
