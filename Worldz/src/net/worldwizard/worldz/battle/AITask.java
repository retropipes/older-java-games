package net.worldwizard.worldz.battle;

import net.worldwizard.worldz.Worldz;

public class AITask extends Thread {
    @Override
    public void run() {
        final Battle b = Worldz.getApplication().getBattle();
        while (b.isWaitingForAI()) {
            b.executeNextAIAction();
            // Delay, for animation purposes
            try {
                final int battleSpeed = Worldz.getApplication()
                        .getPrefsManager().getBattleSpeed();
                Thread.sleep(battleSpeed);
            } catch (final InterruptedException i) {
                // Ignore
            }
            if (b.getTerminatedEarly()) {
                // Bail out of here
                return;
            }
        }
    }
}
