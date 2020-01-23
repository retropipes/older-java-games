/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.battle;

import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.prefs.PreferencesManager;

class AITask extends Thread {
    // Fields
    private final BattleLogic b;
    private boolean done;

    // Constructors
    AITask(BattleLogic battle) {
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
                        int battleSpeed = PreferencesManager.getBattleSpeed();
                        Thread.sleep(battleSpeed);
                    } catch (InterruptedException i) {
                        // Ignore
                    }
                    if (this.b.getTerminatedEarly()) {
                        // Bail out of here
                        return;
                    }
                }
            }
        } catch (Throwable t) {
            Gemma.getErrorLogger().logError(t);
        }
    }

    void turnOver() {
        this.done = true;
    }
}
