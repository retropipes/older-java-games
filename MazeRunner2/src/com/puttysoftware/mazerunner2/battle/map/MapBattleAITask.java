/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.battle.map;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;

class MapBattleAITask extends Thread {
    // Fields
    private final MapBattleLogic b;
    private boolean done;

    // Constructors
    MapBattleAITask(MapBattleLogic battle) {
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
            MazeRunnerII.getErrorLogger().logError(t);
        }
    }

    void turnOver() {
        this.done = true;
    }
}
