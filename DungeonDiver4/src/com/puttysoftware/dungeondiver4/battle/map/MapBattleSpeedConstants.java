/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.map;

public class MapBattleSpeedConstants {
    // Constants
    private static final int BATTLE_SPEED_VERY_FAST = 300;
    private static final int BATTLE_SPEED_FAST = 500;
    public static final int BATTLE_SPEED_MODERATE = 700;
    private static final int BATTLE_SPEED_SLOW = 900;
    private static final int BATTLE_SPEED_VERY_SLOW = 1100;
    public static final int[] BATTLE_SPEED_ARRAY = new int[] {
            BATTLE_SPEED_VERY_SLOW, BATTLE_SPEED_SLOW, BATTLE_SPEED_MODERATE,
            BATTLE_SPEED_FAST, BATTLE_SPEED_VERY_FAST };

    private MapBattleSpeedConstants() {
        // Do nothing
    }
}