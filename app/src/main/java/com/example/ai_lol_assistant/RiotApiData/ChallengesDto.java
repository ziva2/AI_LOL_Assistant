package com.example.ai_lol_assistant.RiotApiData;

import java.util.List;

public class ChallengesDto {
    // General game statistics
    private int assistStreakCount;
    private int baronBuffGoldAdvantageOverThreshold;
    private float controlWardTimeCoverageInRiverOrEnemyHalf;
    private int earliestBaron;
    private int earliestDragonTakedown;
    private int earliestElderDragon;
    private int earlyLaningPhaseGoldExpAdvantage;
    private int fasterSupportQuestCompletion;
    private int fastestLegendary;
    private int hadAfkTeammate;
    private int highestChampionDamage;
    private int highestCrowdControlScore;
    private int highestWardKills;
    private int junglerKillsEarlyJungle;
    private int killsOnLanersEarlyJungleAsJungler;
    private int laningPhaseGoldExpAdvantage;
    private int legendaryCount;

    // Lane and vision statistics
    private float maxCsAdvantageOnLaneOpponent;
    private int maxLevelLeadLaneOpponent;
    private int mostWardsDestroyedOneSweeper;
    private int mythicItemUsed;
    private int playedChampSelectPosition;
    private int soloTurretsLategame;
    private int takedownsFirst25Minutes;
    private int teleportTakedowns;
    private int thirdInhibitorDestroyedTime;
    private int threeWardsOneSweeperCount;
    private float visionScoreAdvantageLaneOpponent;

    // Combat-related statistics
    private int infernalScalePickup;
    private int fistBumpParticipation;
    private int voidMonsterKill;
    private int abilityUses;
    private int acesBefore15Minutes;
    private float alliedJungleMonsterKills;
    private int baronTakedowns;
    private int blastConeOppositeOpponentCount;
    private int bountyGold;
    private int buffsStolen;

    // Support-related statistics
    private int completeSupportQuestInTime;
    private int controlWardsPlaced;
    private float damagePerMinute;
    private float damageTakenOnTeamPercentage;
    private int dancedWithRiftHerald;
    private int deathsByEnemyChamps;
    private int dodgeSkillShotsSmallWindow;
    private int doubleAces;

    // Dragon and epic monster statistics
    private int dragonTakedowns;
    private List<Integer> legendaryItemUsed;
    private float effectiveHealAndShielding;
    private int elderDragonKillsWithOpposingSoul;
    private int elderDragonMultikills;

    // Additional fields
    private float goldPerMinute;
    private float teamDamagePercentage;

    // Getters for all fields
    public int getAssistStreakCount() {
        return assistStreakCount;
    }

    public int getBaronBuffGoldAdvantageOverThreshold() {
        return baronBuffGoldAdvantageOverThreshold;
    }

    public float getControlWardTimeCoverageInRiverOrEnemyHalf() {
        return controlWardTimeCoverageInRiverOrEnemyHalf;
    }

    public int getEarliestBaron() {
        return earliestBaron;
    }

    public int getEarliestDragonTakedown() {
        return earliestDragonTakedown;
    }

    public int getEarliestElderDragon() {
        return earliestElderDragon;
    }

    public int getEarlyLaningPhaseGoldExpAdvantage() {
        return earlyLaningPhaseGoldExpAdvantage;
    }

    public int getFasterSupportQuestCompletion() {
        return fasterSupportQuestCompletion;
    }

    public int getFastestLegendary() {
        return fastestLegendary;
    }

    public int getHadAfkTeammate() {
        return hadAfkTeammate;
    }

    public int getHighestChampionDamage() {
        return highestChampionDamage;
    }

    public int getHighestCrowdControlScore() {
        return highestCrowdControlScore;
    }

    public int getHighestWardKills() {
        return highestWardKills;
    }

    public int getJunglerKillsEarlyJungle() {
        return junglerKillsEarlyJungle;
    }

    public int getKillsOnLanersEarlyJungleAsJungler() {
        return killsOnLanersEarlyJungleAsJungler;
    }

    public int getLaningPhaseGoldExpAdvantage() {
        return laningPhaseGoldExpAdvantage;
    }

    public int getLegendaryCount() {
        return legendaryCount;
    }

    public float getMaxCsAdvantageOnLaneOpponent() {
        return maxCsAdvantageOnLaneOpponent;
    }

    public int getMaxLevelLeadLaneOpponent() {
        return maxLevelLeadLaneOpponent;
    }

    public int getMostWardsDestroyedOneSweeper() {
        return mostWardsDestroyedOneSweeper;
    }

    public int getMythicItemUsed() {
        return mythicItemUsed;
    }

    public int getPlayedChampSelectPosition() {
        return playedChampSelectPosition;
    }

    public int getSoloTurretsLategame() {
        return soloTurretsLategame;
    }

    public int getTakedownsFirst25Minutes() {
        return takedownsFirst25Minutes;
    }

    public int getTeleportTakedowns() {
        return teleportTakedowns;
    }

    public int getThirdInhibitorDestroyedTime() {
        return thirdInhibitorDestroyedTime;
    }

    public int getThreeWardsOneSweeperCount() {
        return threeWardsOneSweeperCount;
    }

    public float getVisionScoreAdvantageLaneOpponent() {
        return visionScoreAdvantageLaneOpponent;
    }

    public int getInfernalScalePickup() {
        return infernalScalePickup;
    }

    public int getFistBumpParticipation() {
        return fistBumpParticipation;
    }

    public int getVoidMonsterKill() {
        return voidMonsterKill;
    }

    public int getAbilityUses() {
        return abilityUses;
    }

    public int getAcesBefore15Minutes() {
        return acesBefore15Minutes;
    }

    public float getAlliedJungleMonsterKills() {
        return alliedJungleMonsterKills;
    }

    public int getBaronTakedowns() {
        return baronTakedowns;
    }

    public int getBlastConeOppositeOpponentCount() {
        return blastConeOppositeOpponentCount;
    }

    public int getBountyGold() {
        return bountyGold;
    }

    public int getBuffsStolen() {
        return buffsStolen;
    }

    public int getCompleteSupportQuestInTime() {
        return completeSupportQuestInTime;
    }

    public int getControlWardsPlaced() {
        return controlWardsPlaced;
    }

    public float getDamagePerMinute() {
        return damagePerMinute;
    }

    public float getDamageTakenOnTeamPercentage() {
        return damageTakenOnTeamPercentage;
    }

    public int getDancedWithRiftHerald() {
        return dancedWithRiftHerald;
    }

    public int getDeathsByEnemyChamps() {
        return deathsByEnemyChamps;
    }

    public int getDodgeSkillShotsSmallWindow() {
        return dodgeSkillShotsSmallWindow;
    }

    public int getDoubleAces() {
        return doubleAces;
    }

    public int getDragonTakedowns() {
        return dragonTakedowns;
    }

    public List<Integer> getLegendaryItemUsed() {
        return legendaryItemUsed;
    }

    public float getEffectiveHealAndShielding() {
        return effectiveHealAndShielding;
    }

    public int getElderDragonKillsWithOpposingSoul() {
        return elderDragonKillsWithOpposingSoul;
    }

    public int getElderDragonMultikills() {
        return elderDragonMultikills;
    }

    public float getGoldPerMinute() {
        return goldPerMinute;
    }

    public float getTeamDamagePercentage() {
        return teamDamagePercentage;
    }
}
