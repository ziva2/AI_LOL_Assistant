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
    } // 어댑터 초기화 시 Context와 매치 데이터 리스트를 전달

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_match_row, parent, false);
        return new ViewHolder(view);
    } //각 아이템의 레이아웃(item_match_row.xml)을 생성, 레이아웃을 뷰로 변환한 후 ViewHolder에 전달

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchDto match = matchList.get(position);
        ParticipantDto participant = getPlayerFromMatch(match);

        holder.tvChampion.setText(participant.getChampionName());
        holder.tvKDA.setText(String.format("%d/%d/%d", participant.getKills(), participant.getDeaths(), participant.getAssists()));
        holder.tvWinLose.setText(participant.isWin() ? "승리" : "패배");

        // 더보기 버튼 클릭 시 상세 정보 토글, 매치 데이터를 matchList에서 가져온다.
        // 각 MatchDto의 ParticipantDto(참여자 정보)에서 플레이어 데이터를 추출해 뷰에 바인딩한다.
        holder.btnMore.setOnClickListener(v -> {
            if (holder.llDetails.getVisibility() == View.GONE) {
                holder.llDetails.setVisibility(View.VISIBLE);
            } else {
                holder.llDetails.setVisibility(View.GONE);
            }
        });

        // 상세 정보 표시
        holder.tvTeamInfo.setText("팀 정보: " + getTeamInfo(match));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    } // 매치 리스트의 크기를 반환한다. 리사이클러 뷰가 표시할 아이템 개수를 결정한다.

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChampion, tvKDA, tvWinLose, tvTeamInfo;
        Button btnMore;
        LinearLayout llDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            tvWinLose = itemView.findViewById(R.id.tvWinLose);
            btnMore = itemView.findViewById(R.id.btnMore);
            llDetails = itemView.findViewById(R.id.llDetails);
            tvTeamInfo = itemView.findViewById(R.id.tvTeamInfo);
        } //item_match_row.xml 레이아웃 파일에 정의된 뷰 ID를 참조
    }

    private ParticipantDto getPlayerFromMatch(MatchDto match) {
        // 매치에서 플레이어 정보 가져오기 (기본 로직)
        for (ParticipantDto player : match.getInfo().getParticipants()){
            if (player.getPuuid().equals(puuid)){
                return player;
            }
        }
        return match.getInfo().getParticipants().get(0); //정보가 안깨지면 무조건 안들어감
    }

    private String getTeamInfo(MatchDto match) {
        // 팀 정보를 문자열로 반환하는 로직 추가
        return "팀1: 플레이어1, 팀2: 플레이어2"; // Placeholder
    }
}

