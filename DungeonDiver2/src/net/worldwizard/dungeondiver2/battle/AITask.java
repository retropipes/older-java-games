package net.worldwizard.dungeondiver2.battle;

import net.worldwizard.dungeondiver2.prefs.PreferencesManager;

public class AITask extends Thread {
    // Fields
    private final BattleGUI b;

    // Constructors
    public AITask(final BattleGUI battle) {
        this.b = battle;
    }

    @Override
    public void run() {
        while (this.b.isWaitingForAI()) {
            this.b.executeNextAIAction();
            if (this.b.getLastAIActionResult()) {
                // Delay, for animation purposes
                try {
                    final int battleSpeed = PreferencesManager.getBattleSpeed();
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
    }
}
