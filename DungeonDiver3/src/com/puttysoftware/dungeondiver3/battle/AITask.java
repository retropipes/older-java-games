/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.battle;

import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.prefs.PreferencesManager;

class AITask extends Thread {
    // Fields
    private final BattleLogic b;
    private boolean done;

    // Constructors
    AITask(final BattleLogic battle) {
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
            DungeonDiver3.getErrorLogger().logError(t);
        }
    }

    void turnOver() {
        this.done = true;
    }
}
