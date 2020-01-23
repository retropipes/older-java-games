/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.battle;

public class BattleSpeedConstants {
    // Constants
    private static final int BATTLE_SPEED_VERY_FAST = 300;
    private static final int BATTLE_SPEED_FAST = 500;
    public static final int BATTLE_SPEED_MODERATE = 700;
    private static final int BATTLE_SPEED_SLOW = 900;
    private static final int BATTLE_SPEED_VERY_SLOW = 1100;
    public static final int[] BATTLE_SPEED_ARRAY = new int[] {
            BattleSpeedConstants.BATTLE_SPEED_VERY_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_SLOW,
            BattleSpeedConstants.BATTLE_SPEED_MODERATE,
            BattleSpeedConstants.BATTLE_SPEED_FAST,
            BattleSpeedConstants.BATTLE_SPEED_VERY_FAST };

    private BattleSpeedConstants() {
        // Do nothing
    }
}