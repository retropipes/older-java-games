package net.worldwizard.dungeondiver2.battle;

public class BattleSpeedConstants {
    // Constants
    public static final int BATTLE_SPEED_VERY_FAST = 500;
    public static final int BATTLE_SPEED_FAST = 750;
    public static final int BATTLE_SPEED_MODERATE = 1000;
    public static final int BATTLE_SPEED_SLOW = 1250;
    public static final int BATTLE_SPEED_VERY_SLOW = 1500;
    public static final int[] BATTLE_SPEED_ARRAY = new int[] {
            BattleSpeedConstants.BATTLE_SPEED_VERY_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_MODERATE,
            BattleSpeedConstants.BATTLE_SPEED_FAST,
            BattleSpeedConstants.BATTLE_SPEED_VERY_FAST };
}
