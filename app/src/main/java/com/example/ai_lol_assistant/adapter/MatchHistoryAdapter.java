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
import com.example.ai_lol_assistant.model.ChallengesDto;
import com.example.ai_lol_assistant.model.InfoDto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import android.util.Log;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder> {
    private final Context context;
    private final List<MatchDto> matchList;
    private final String puuid;

    private ChallengesDto challengesDto;

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
        challengesDto = participant.getChallenges();

        holder.tvChampion.setText(participant.getChampionName());
        holder.tvKDA.setText(String.format("%d/%d/%d", participant.getKills(), participant.getDeaths(), participant.getAssists()));
        holder.tvWinLose.setText(participant.isWin() ? "승리" : "패배");

        // Set background color based on win/lose
        holder.itemView.setBackgroundColor(participant.isWin() ? Color.parseColor("#deecff") : Color.parseColor("#ffdede"));

        // 게임 모드 표시
        holder.tvGameMode.setText(match.getInfo().getGameMode());
        holder.tvGameMode.setTypeface(null, Typeface.BOLD);

        // 로그 출력: 필드 값 확인
        Log.d("MatchHistoryAdapter", "---------- 디버깅 정보 ----------");
        Log.d("MatchHistoryAdapter", "ChampionName: " + participant.getChampionName());
        Log.d("MatchHistoryAdapter", "Kills/Deaths/Assists: " + participant.getKills() + "/" + participant.getDeaths() + "/" + participant.getAssists());
        Log.d("MatchHistoryAdapter", "Largest Killing Spree: " + participant.getLargestKillingSpree());
        Log.d("MatchHistoryAdapter", "Objectives Stolen: " + participant.getObjectivesStolen());
        Log.d("MatchHistoryAdapter", "Total Damage Taken: " + participant.getTotalDamageTaken());
        Log.d("MatchHistoryAdapter", "Total Heal: " + participant.getTotalHeal());
        Log.d("MatchHistoryAdapter", "Turret Kills: " + participant.getTurretKills());
        Log.d("MatchHistoryAdapter", "Longest Time Spent Living: " + participant.getLongestTimeSpentLiving());

        // ChallengesDto 필드 확인
        if (challengesDto != null) {
            Log.d("MatchHistoryAdapter", "Kill After Hidden With Ally: " + challengesDto.getKillAfterHiddenWithAlly());
            Log.d("MatchHistoryAdapter", "Damage Taken on Team Percentage: " + challengesDto.getDamageTakenOnTeamPercentage());
            Log.d("MatchHistoryAdapter", "Save Ally From Death: " + challengesDto.getSaveAllyFromDeath());
            Log.d("MatchHistoryAdapter", "Solo Turrets Late Game: " + challengesDto.getSoloTurretsLategame());
            Log.d("MatchHistoryAdapter", "Outnumbered Kills: " + challengesDto.getOutnumberedKills());
            Log.d("MatchHistoryAdapter", "Laning Phase Gold/Exp Advantage: " + challengesDto.getLaningPhaseGoldExpAdvantage());
            Log.d("MatchHistoryAdapter", "Skillshots Dodged: " + challengesDto.getSkillshotsDodged());
        } else {
            Log.d("MatchHistoryAdapter", "ChallengesDto is NULL");
        }

        holder.btnMore.setOnClickListener(v -> {

            // 애니메이션 로드 및 실행
            Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.click_scale);
            holder.btnMore.startAnimation(scaleAnimation);

            if (holder.scrollViewDetails.getVisibility() == View.GONE) {
                holder.scrollViewDetails.setVisibility(View.VISIBLE); // ScrollView 보이기
                displayMatchDetails(holder.llDetails, match, participant); // 세부 내용 추가
                holder.btnMore.setText("닫기");
            } else {
                holder.scrollViewDetails.setVisibility(View.GONE); // ScrollView 숨기기
                holder.btnMore.setText("더보기");
            }
        });
    }


    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChampion, tvKDA, tvWinLose, tvGameMode;
        Button btnMore;
        LinearLayout llDetails;
        android.widget.ScrollView scrollViewDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChampion = itemView.findViewById(R.id.tvChampion);
            tvKDA = itemView.findViewById(R.id.tvKDA);
            tvWinLose = itemView.findViewById(R.id.tvWinLose);
            tvGameMode = itemView.findViewById(R.id.tvGameMode);
            btnMore = itemView.findViewById(R.id.btnMore);

            // ScrollView와 세부 레이아웃 참조
            scrollViewDetails = itemView.findViewById(R.id.scrollViewDetails);
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

    // 유틸 메서드: 값과 순위를 포맷팅
    private String formatValue(int value, String message) {
        return value > 0 ? message.replace("N", String.valueOf(value)) : null;
    }

    private String formatPercentage(double value, String message) {
        return value > 0 ? message.replace("N", String.format("%.1f", value * 100)) : null;
    }

    private String formatRankAndValue(List<ParticipantDto> participants, Comparator<ParticipantDto> comparator, int value, String message) {
        if (value > 0) {
            int rank = calculateRank(participants, comparator, value);
            if (rank != -1) {
                return message.replace("N", String.valueOf(value)).replace("n등", rank + "등");
            }
        }
        return null;
    }

    // 유틸 메서드: 분석 라인 추가
    private boolean addAnalysisLine(LinearLayout section, String text) {
        if (text != null) {
            TextView textView = new TextView(section.getContext());
            textView.setText(text);
            textView.setPadding(8, 4, 8, 4);
            section.addView(textView);
            return true;
        }
        return false;
    }

    // 섹션 레이아웃 생성
    private LinearLayout createSectionLayout(ViewGroup parent, String title) {
        LinearLayout sectionLayout = new LinearLayout(parent.getContext());
        sectionLayout.setOrientation(LinearLayout.VERTICAL);
        sectionLayout.setPadding(12, 12, 12, 12);
        sectionLayout.setBackground(createRoundedBackground());

        TextView sectionTitle = new TextView(parent.getContext());
        sectionTitle.setText(title);
        sectionTitle.setTypeface(null, Typeface.BOLD);
        sectionTitle.setPadding(0, 8, 0, 8);
        sectionTitle.setGravity(android.view.Gravity.CENTER);

        sectionLayout.addView(sectionTitle);
        return sectionLayout;
    }

    // 둥근 테마 배경 생성
    private GradientDrawable createRoundedBackground() {
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor("#f6f2f7"));
        background.setCornerRadius(28f);
        return background;
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
        String damageRank = this.calculateRank2(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalDamageDealt), currentPlayer.getTotalDamageDealt());
        String goldRank = this.calculateRank2(allParticipants, Comparator.comparingInt(ParticipantDto::getGoldEarned), currentPlayer.getGoldEarned());
        String minionRank = this.calculateRank2(allParticipants, Comparator.comparingInt(ParticipantDto::getTotalMinionsKilled), currentPlayer.getTotalMinionsKilled());

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
        createAdditionalAnalysisSection(detailsLayout, allParticipants, currentPlayer);

    }
    // 추가할 부분: 추가 분석 섹션 생성
    private void createAdditionalAnalysisSection(LinearLayout detailsLayout, List<ParticipantDto> allParticipants, ParticipantDto currentPlayer) {
        boolean hasContent = false;
        LinearLayout additionalAnalysisSection = createSectionLayout(detailsLayout, "추가 분석!");


        // 나의 전투 기여도
        String largestKillingSpree = formatValue(currentPlayer.getLargestKillingSpree(), "연속으로 죽지 않고 N번 적을 처치"); //getLargestKillingSpree
        String objectivesStolen = formatValue(currentPlayer.getObjectivesStolen(), "오브젝트(드래곤, 내셔 남작 ..)는 N번 스틸");
        String killAfterHidden = formatValue(challengesDto.getKillAfterHiddenWithAlly(), "아군과 매복 후 N번 킬 성공"); // killAfterHiddenWithAlly
        String damageTakenPct = formatPercentage(challengesDto.getDamageTakenOnTeamPercentage(), "팀 총 피해 중 내 비율 N%"); //damageTakenOnTeamPercentage
        String saveAlly = formatValue(challengesDto.getSaveAllyFromDeath(), "아군을 죽음에서 N번 구함"); // saveAllyFromDeath

        // 나의 캐리력
        String soloTurrets = formatRankAndValue(allParticipants, Comparator.comparingInt(p -> p.getChallenges().getSoloTurretsLategame()), currentPlayer.getChallenges().getSoloTurretsLategame(), "혼자 파괴한 포탑 수: N (팀 중 n등)");
        String outnumberedKills = formatRankAndValue(allParticipants, Comparator.comparingInt(p -> p.getChallenges().getOutnumberedKills()), currentPlayer.getChallenges().getOutnumberedKills(), "내가 열세 상황일 때 나는 N 킬을 성공");

        // 라인전 능력
        String turretKills = formatRankAndValue(allParticipants, Comparator.comparingInt(ParticipantDto::getTurretKills), currentPlayer.getTurretKills(), "파괴한 포탑 수는 N개");
        String laningAdvantage = formatPercentage(currentPlayer.getChallenges().getLaningPhaseGoldExpAdvantage(), "라인전 골드/경험치 우위 N%");

        // 나의 생존력
        String longestLiving = formatRankAndValue(allParticipants, Comparator.comparingInt(ParticipantDto::getLongestTimeSpentLiving), currentPlayer.getLongestTimeSpentLiving(), "죽지 않고 생존한 시간: N초");
        String skillshotsDodged = formatRankAndValue(allParticipants, Comparator.comparingInt(p -> p.getChallenges().getSkillshotsDodged()), currentPlayer.getChallenges().getSkillshotsDodged(), "피한 스킬샷: N번");

        // 각 항목 추가
        hasContent |= addAnalysisLine(additionalAnalysisSection, largestKillingSpree);
        hasContent |= addAnalysisLine(additionalAnalysisSection, objectivesStolen);
        hasContent |= addAnalysisLine(additionalAnalysisSection, killAfterHidden);
        hasContent |= addAnalysisLine(additionalAnalysisSection, damageTakenPct);
        hasContent |= addAnalysisLine(additionalAnalysisSection, saveAlly);

        hasContent |= addAnalysisLine(additionalAnalysisSection, soloTurrets);
        hasContent |= addAnalysisLine(additionalAnalysisSection, outnumberedKills);

        hasContent |= addAnalysisLine(additionalAnalysisSection, turretKills);
        hasContent |= addAnalysisLine(additionalAnalysisSection, laningAdvantage);

        hasContent |= addAnalysisLine(additionalAnalysisSection, longestLiving);
        hasContent |= addAnalysisLine(additionalAnalysisSection, skillshotsDodged);

        // 섹션 추가 여부 확인
        if (hasContent) {
            // 투명한 패딩 추가
            View spacer = new View(detailsLayout.getContext());
            spacer.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 16)); // 높이 16px
            spacer.setBackgroundColor(Color.TRANSPARENT);
            detailsLayout.addView(spacer);

            detailsLayout.addView(additionalAnalysisSection);
        }
    }


    private int calculateRank(List<ParticipantDto> participants, Comparator<ParticipantDto> comparator, int currentPlayerValue) {
        // 내림차순 정렬
        List<ParticipantDto> sortedList = participants.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());

        int rank = 1;
        for (ParticipantDto participant : sortedList) {
            int compareValue;

            // Comparator에 따라 비교할 필드 결정
            if (comparator == Comparator.comparingInt(ParticipantDto::getTotalDamageDealt)) {
                compareValue = participant.getTotalDamageDealt();
            } else if (comparator == Comparator.comparingInt(ParticipantDto::getGoldEarned)) {
                compareValue = participant.getGoldEarned();
            } else if (comparator == Comparator.comparingInt(ParticipantDto::getTotalMinionsKilled)) {
                compareValue = participant.getTotalMinionsKilled();
            } else {
                continue; // 정의되지 않은 Comparator는 스킵
            }

            // compareValue와 currentPlayerValue 비교
            if (compareValue == currentPlayerValue) {
                return rank; // 값이 같으면 현재 순위 반환
            }
            rank++;
        }

        return -1; // 순위를 계산할 수 없는 경우
    }

    private String calculateRank2(List<ParticipantDto> participants, Comparator<ParticipantDto> comparator, int currentPlayerValue) {
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