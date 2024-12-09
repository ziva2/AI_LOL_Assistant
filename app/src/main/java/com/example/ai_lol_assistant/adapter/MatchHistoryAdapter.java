package com.example.ai_lol_assistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.MatchDto;
import com.example.ai_lol_assistant.model.ParticipantDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        // Set background color based on win/lose
        holder.itemView.setBackgroundColor(participant.isWin() ? Color.parseColor("#deecff") : Color.parseColor("#ffdede"));

        holder.btnMore.setOnClickListener(v -> {
            if (holder.llDetails.getVisibility() == View.GONE) {
                holder.llDetails.setVisibility(View.VISIBLE);
                displayMatchDetails(holder.llDetails, match, participant);
            } else {
                holder.llDetails.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChampion, tvKDA, tvWinLose;
        Button btnMore;
        LinearLayout llDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            tvWinLose = itemView.findViewById(R.id.tvWinLose);
            btnMore = itemView.findViewById(R.id.btnMore);
            llDetails = itemView.findViewById(R.id.llDetails);
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

    private void displayMatchDetails(LinearLayout detailsLayout, MatchDto match, ParticipantDto currentPlayer) {
        detailsLayout.removeAllViews();

        List<ParticipantDto> allParticipants = match.getInfo().getParticipants();
        List<ParticipantDto> myTeam = allParticipants.stream()
                .filter(p -> p.getTeamId() == 100)
                .collect(Collectors.toList());
        List<ParticipantDto> opponentTeam = allParticipants.stream()
                .filter(p -> p.getTeamId() == 200)
                .collect(Collectors.toList());

        // Add table header
        TableLayout tableLayout = new TableLayout(detailsLayout.getContext());
        TableRow headerRow = new TableRow(detailsLayout.getContext());

        TextView headerMyTeam = new TextView(detailsLayout.getContext());
        headerMyTeam.setText("   아군");
        headerMyTeam.setPadding(8, 4, 8, 4);
        headerMyTeam.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView headerKDA = new TextView(detailsLayout.getContext());
        headerKDA.setText(" K/D/A");
        headerKDA.setPadding(8, 4, 8, 4);
        headerKDA.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView headerOpponentTeam = new TextView(detailsLayout.getContext());
        headerOpponentTeam.setText("   적군");
        headerOpponentTeam.setPadding(8, 4, 8, 4);
        headerOpponentTeam.setTypeface(null, android.graphics.Typeface.BOLD);

        TextView headerOpponentKDA = new TextView(detailsLayout.getContext());
        headerOpponentKDA.setText(" K/D/A");
        headerOpponentKDA.setPadding(8, 4, 8, 4);
        headerOpponentKDA.setTypeface(null, android.graphics.Typeface.BOLD);

        headerRow.addView(headerMyTeam);
        headerRow.addView(headerKDA);
        headerRow.addView(headerOpponentTeam);
        headerRow.addView(headerOpponentKDA);
        tableLayout.addView(headerRow);

        // Add participant data
        for (int i = 0; i < myTeam.size(); i++) {
            ParticipantDto myParticipant = myTeam.get(i);
            ParticipantDto opponentParticipant = opponentTeam.get(i);

            TableRow row = new TableRow(detailsLayout.getContext());

            TextView myTeamInfo = new TextView(detailsLayout.getContext());
            myTeamInfo.setText(myParticipant.getChampionName());
            myTeamInfo.setPadding(20, 15, 20, 15);
            myTeamInfo.setTypeface(null, android.graphics.Typeface.BOLD);

            TextView myKDAInfo = new TextView(detailsLayout.getContext());
            myKDAInfo.setText(String.format("%d/%d/%d", myParticipant.getKills(), myParticipant.getDeaths(), myParticipant.getAssists()));
            myKDAInfo.setPadding(20, 15, 20, 15);

            TextView opponentTeamInfo = new TextView(detailsLayout.getContext());
            opponentTeamInfo.setText(opponentParticipant.getChampionName());
            opponentTeamInfo.setPadding(20, 15, 20, 15);
            opponentTeamInfo.setTypeface(null, android.graphics.Typeface.BOLD);

            TextView opponentKDAInfo = new TextView(detailsLayout.getContext());
            opponentKDAInfo.setText(String.format("%d/%d/%d", opponentParticipant.getKills(), opponentParticipant.getDeaths(), opponentParticipant.getAssists()));
            opponentKDAInfo.setPadding(20, 15, 20, 15);

            row.addView(myTeamInfo);
            row.addView(myKDAInfo);
            row.addView(opponentTeamInfo);
            row.addView(opponentKDAInfo);
            tableLayout.addView(row);
        }

        // Add rank information
        String damageRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalDamageDealt), currentPlayer.getTotalDamageDealt());
        String goldRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getGoldEarned), currentPlayer.getGoldEarned());
        String minionRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalMinionsKilled), currentPlayer.getTotalMinionsKilled());

        TextView rankInfo = new TextView(detailsLayout.getContext());
        rankInfo.setText(String.format("딜량 순위: %s\n골드 획득 순위: %s\n미니언 순위: %s", damageRank, goldRank, minionRank));
        rankInfo.setPadding(8, 16, 8, 16);

        detailsLayout.addView(tableLayout);
        detailsLayout.addView(rankInfo);
    }

    private String calculateRank(List<ParticipantDto> participants, Comparator<ParticipantDto> comparator, int currentPlayerValue) {
        // 내림차순으로 정렬
        List<ParticipantDto> sortedList = participants.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());

        int rank = 1;
        for (ParticipantDto participant : sortedList) {
            // 현재 플레이어의 값과 비교
            if (comparator.compare(participant, new ParticipantDto() {
                @Override
                public int getTotalDamageDealt() {
                    return currentPlayerValue;
                }

                @Override
                public int getGoldEarned() {
                    return currentPlayerValue;
                }

                @Override
                public int getTotalMinionsKilled() {
                    return currentPlayerValue;
                }
            }) == 0) {
                return rank == 1 ? "축하합니다! 1등입니다!" : rank + "등";
            }
            rank++;
        }
        return "순위를 계산할 수 없습니다.";
    }


    private String formatParticipantInfo(ParticipantDto participant) {
        return participant.getChampionName() + " " +
                participant.getKills() + "/" +
                participant.getDeaths() + "/" +
                participant.getAssists();
    }
}
