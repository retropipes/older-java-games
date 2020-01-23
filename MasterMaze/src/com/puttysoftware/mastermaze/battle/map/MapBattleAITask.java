/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.battle.map;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.prefs.PreferencesManager;

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
            MasterMaze.getErrorLogger().logError(t);
        }
    }

    void turnOver() {
        this.done = true;
    }
}
