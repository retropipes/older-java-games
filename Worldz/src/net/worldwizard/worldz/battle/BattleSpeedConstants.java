package net.worldwizard.worldz.battle;

public interface BattleSpeedConstants {
    // Constants
    int BATTLE_SPEED_VERY_FAST = 200;
    int BATTLE_SPEED_FAST = 400;
    int BATTLE_SPEED_MODERATE = 600;
    int BATTLE_SPEED_SLOW = 800;
    int BATTLE_SPEED_VERY_SLOW = 1000;
    int[] BATTLE_SPEED_ARRAY = new int[] {
            BattleSpeedConstants.BATTLE_SPEED_VERY_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_MODERATE,
            BattleSpeedConstants.BATTLE_SPEED_FAST,
            BattleSpeedConstants.BATTLE_SPEED_VERY_FAST };
}
