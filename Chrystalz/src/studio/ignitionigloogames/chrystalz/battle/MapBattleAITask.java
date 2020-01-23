/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;

public class MapBattleAITask extends Thread {
    // Fields
    private final AbstractBattle b;

    // Constructors
    public MapBattleAITask(final AbstractBattle battle) {
        this.setName("Map AI Runner");
        this.b = battle;
    }

    @Override
    public void run() {
        try {
            this.aiWait();
            while (true) {
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
                }
            }
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        }
    }

    public synchronized void aiWait() {
        try {
            this.wait();
        } catch (final InterruptedException e) {
            // Ignore
        }
    }

    public synchronized void aiRun() {
        this.notify();
    }
}
