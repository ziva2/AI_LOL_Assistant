package com.example.ai_lol_assistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.ai_lol_assistant.model.InfoDto;

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

        // 게임 모드 표시
        holder.tvGameMode.setText(match.getInfo().getGameMode());
        holder.tvGameMode.setTypeface(null, Typeface.BOLD);

        // 버튼 클릭 시 애니메이션 적용
        holder.btnMore.setOnClickListener(v -> {
            // 애니메이션 로드 및 실행
            Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.click_scale);
            holder.btnMore.startAnimation(scaleAnimation);

            // 상세 정보 토글
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
        TextView tvChampion, tvKDA, tvWinLose, tvGameMode;;
        Button btnMore;
        LinearLayout llDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            tvWinLose = itemView.findViewById(R.id.tvWinLose);
            btnMore = itemView.findViewById(R.id.btnMore);
            llDetails = itemView.findViewById(R.id.llDetails);
            tvGameMode = itemView.findViewById(R.id.tvGameMode);
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

    // 수정된 부분: 라벨을 굵게 설정하는 메서드
    private void addBoldLabelTextView(LinearLayout parent, String label, String value) {
        LinearLayout container = new LinearLayout(parent.getContext());
        container.setOrientation(LinearLayout.HORIZONTAL);

        TextView labelView = new TextView(parent.getContext());
        labelView.setText(label);
        labelView.setTypeface(null, Typeface.BOLD); // 라벨을 굵게 설정
        labelView.setPadding(8, 4, 8, 4);

        TextView valueView = new TextView(parent.getContext());
        valueView.setText(value);
        valueView.setPadding(8, 4, 8, 4);

        container.addView(labelView);
        container.addView(valueView);

        parent.addView(container);
    }

    private void displayMatchDetails(LinearLayout detailsLayout, MatchDto match, ParticipantDto currentPlayer) {
        detailsLayout.removeAllViews();

        // 수정된 부분: 게임 모드 표시
        TextView gameModeView = new TextView(detailsLayout.getContext());
        gameModeView.setText("게임 모드: " + match.getInfo().getGameMode());
        gameModeView.setTypeface(null, Typeface.BOLD);
        gameModeView.setPadding(8, 8, 8, 8);
        detailsLayout.addView(gameModeView);

        List<ParticipantDto> allParticipants = match.getInfo().getParticipants();
        List<ParticipantDto> myTeam = allParticipants.stream()
                .filter(p -> p.getTeamId() == 100)
                .collect(Collectors.toList());
        List<ParticipantDto> opponentTeam = allParticipants.stream()
                .filter(p -> p.getTeamId() == 200)
                .collect(Collectors.toList());

        // Add table header
        // Add table header
        TableLayout tableLayout = new TableLayout(detailsLayout.getContext());
        TableRow headerRow = new TableRow(detailsLayout.getContext());

        // 수정된 부분: 헤더 배경에 투명도 추가
        GradientDrawable headerBackground = new GradientDrawable();
        headerBackground.setColor(Color.parseColor("#f6f2f7")); // #F2 = 95% 투명도
        headerBackground.setCornerRadius(28f); // 모서리 반경 설정

        headerRow.setBackground(headerBackground);
        headerRow.setPadding(8, 8, 8, 8);

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

            // 수정된 부분: GradientDrawable에 투명도 추가
            GradientDrawable rowBackground = new GradientDrawable();
            rowBackground.setColor(Color.parseColor("#59f6f2f7")); //99 = 60%, 73 = 45%, 59 = 35%, 80 = 50%, 8C = 55%
            rowBackground.setCornerRadius(28f); // 모서리 반경 설정

            row.setBackground(rowBackground); // 테마 적용

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


        // 플레이 분석 섹션
        LinearLayout playAnalysisSection = new LinearLayout(detailsLayout.getContext());
        playAnalysisSection.setOrientation(LinearLayout.VERTICAL);

        // 수정된 부분: 모서리 곡률을 추가한 GradientDrawable 생성
        GradientDrawable roundedBackground = new GradientDrawable();
        roundedBackground.setColor(Color.parseColor("#f6f2f7")); // 배경색
        roundedBackground.setCornerRadius(28f); // 모서리 반경 설정 (16dp)

        // 섹션에 적용
        playAnalysisSection.setBackground(roundedBackground);
        playAnalysisSection.setPadding(12, 12, 12, 12);


        TextView playAnalysisTitle = new TextView(detailsLayout.getContext());
        playAnalysisTitle.setText("소환사님의 플레이");
        playAnalysisTitle.setTypeface(null, Typeface.BOLD);
        playAnalysisTitle.setGravity(android.view.Gravity.CENTER);
        playAnalysisTitle.setPadding(0, 8, 0, 8);
        playAnalysisSection.addView(playAnalysisTitle);

        // 플레이 분석 타이틀
        View thinLine = new View(detailsLayout.getContext());
        thinLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2));
        thinLine.setBackgroundColor(Color.LTGRAY);
        playAnalysisSection.addView(thinLine);

        // Add rank information
        String damageRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalDamageDealt), currentPlayer.getTotalDamageDealt());
        String goldRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getGoldEarned), currentPlayer.getGoldEarned());
        String minionRank = this.calculateRank(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalMinionsKilled), currentPlayer.getTotalMinionsKilled());

        addBoldLabelTextView(playAnalysisSection, "⚔️ 나의 딜량 순위: ", damageRank);
        addBoldLabelTextView(playAnalysisSection, "💰 골드 획득량 순위: ", goldRank);
        addBoldLabelTextView(playAnalysisSection, "🍄‍🟫 미니언 처치 수 순위: ", minionRank);

        // Add a transparent spacer before "플레이 분석 섹션"
        View spacer = new View(detailsLayout.getContext());
        spacer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 32)); // 32px 또는 원하는 높이 설정
        spacer.setBackgroundColor(Color.TRANSPARENT); // 투명 배경

        detailsLayout.addView(tableLayout); // 적군, 아군 정보
        detailsLayout.addView(spacer);      // 빈 공간 추가
        detailsLayout.addView(playAnalysisSection); // 플레이 분석 섹션
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
                return rank == 1 ? "축하해🥳 1등!" : rank + "등";
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