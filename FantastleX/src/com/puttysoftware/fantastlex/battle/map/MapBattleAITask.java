/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.battle.map;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;

class MapBattleAITask extends Thread {
    // Fields
    private final MapBattleLogic b;
    private boolean done;

    // Constructors
    MapBattleAITask(final MapBattleLogic battle) {
        this.setName("AI Runner");
        this.b = battle;
        this.done = false;
    }

    @Override
    public void run() {
        try {
            while (!this.done && this.b.isWaitingForAI()) {
                this.b.executeNextAIAction();
                if (this.b.getLastAIActionResult()) {
                    // Delay, for animation purposes
                    try {
                        final int battleSpeed = PreferencesManager
                                .getBattleSpeed();
                        Thread.sleep(battleSpeed);
                    } catch (final InterruptedException i) {
                        // Ignore
                    }
                    if (this.b.getTerminatedEarly()) {
                        // Bail out of here
                        return;
                    }
                }
            }
        } catch (final Throwable t) {
            FantastleX.getErrorLogger().logError(t);
        }
    }

    void turnOver() {
        this.done = true;
    }
}
