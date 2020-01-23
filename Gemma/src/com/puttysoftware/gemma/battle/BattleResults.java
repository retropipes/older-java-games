/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

class BattleResults {
    public static final int IN_PROGRESS = 0;
    public static final int WON = 1;
    public static final int LOST = 2;
    public static final int DRAW = 3;
    public static final int FLED = 4;
    public static final int ENEMY_FLED = 5;

    private BattleResults() {
        // Do nothing
    }
}
